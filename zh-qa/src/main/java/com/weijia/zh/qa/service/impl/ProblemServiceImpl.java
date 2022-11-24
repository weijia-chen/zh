package com.weijia.zh.qa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijia.zh.common.entity.CollectEntity;
import com.weijia.zh.common.entity.MessageEntity;
import com.weijia.zh.common.entity.UserEntity;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.config.HandlerInterceptor;
import com.weijia.zh.qa.dao.*;
import com.weijia.zh.qa.entity.LikeEntity;
import com.weijia.zh.qa.entity.TopicEntity;
import com.weijia.zh.qa.feign.SearchProblem;
import com.weijia.zh.common.utils.RespData;
import com.weijia.zh.qa.entity.ReplyEntity;
import com.weijia.zh.qa.exception.DatabaseException;

import com.weijia.zh.qa.feign.UserFeign;
import com.weijia.zh.qa.key.Key;
import com.weijia.zh.qa.service.RabbitmqProduct;
import com.weijia.zh.qa.service.ReplyService;
import com.weijia.zh.qa.utils.RedisUtil;
import com.weijia.zh.qa.vo.HotProblem;
import com.weijia.zh.qa.vo.ProblemAndReply;
import com.weijia.zh.qa.vo.ProblemVo;
import com.weijia.zh.qa.vo.RespBeanEnum;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.weijia.zh.qa.entity.ProblemEntity;
import com.weijia.zh.qa.service.ProblemService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@Service("problemService")
public class ProblemServiceImpl  implements ProblemService {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    @Qualifier("threadPoolTaskExecutor")//指定注入bean的名称
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private UserFeign userFeign;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SearchProblem searchProblem;

    @Autowired
    private Key key;


    @Autowired
    private TopicDao topicDao;

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private RabbitmqProduct rabbitmqProduct;

    @Autowired
    @Lazy
    private ReplyService replyService;


    /**
     * 1、插入到表格
     * 2、投递到消息队列
     * 3、加入到redis中，记录浏览量
     * 4、全文检索
     *
     * 异步优化：2，3，4其实对用户来说是没有任何影响的，只要存到数据库成功，那就是提问成功
     * 而2，3，4步都可以并行执行，且主线程无需等待2，3，4步执行完成再返回。而且需要网络，所以比较慢
     * 所以2，3，4步可以直接开启3个线程
     * @param problem
     * @return
     */
    @Override
    public R insertProblem(ProblemEntity problem) {

//        1.
        UserEntity user = HandlerInterceptor.loginUser.get();
        log.info("利用cookie和session得到发起请求的用户是,{}",user);
        if (user == null)
            return  new R(RespBeanEnum.LOGIN_ADMINS_ERROR,null);
        problem.setUserId(user.getId());
        problem.setUserName(user.getNickName());
        problem.setUpdateTime(new Date());
        problem.setCreateTime(new Date());
        problemDao.insert(problem);
        log.info("完成第一步插入到表");
        log.info("user{}",user);

//        2.
        CompletableFuture.runAsync(()->{

            log.info("问题插入数据库成功，添加到mq");
//            查询话题名称
            QueryWrapper<TopicEntity> wrapper = new QueryWrapper<>();
            wrapper.select("id","topic_name");
            wrapper.eq("id",problem.getTopicId());
            TopicEntity topicEntity = topicDao.selectOne(wrapper);
            log.info("查询得到topic:{}",topicEntity);

//            1.关注的人发布问题
            MessageEntity message = new MessageEntity();
            message.setKind(2);
            message.setProblemTitle(problem.getTitle());
            message.setProblemId(problem.getId());
            message.setFromUserName(user.getNickName());
            message.setFromUserId(user.getId());
            message.setCreateTime(new Date());
            log.info("关注的人发布问题：{}",message);
            rabbitmqProduct.messageFollow(message);

//            2.关注的话题发布问题
            message.setTopicId(topicEntity.getId());
            message.setTopicName(topicEntity.getTopicName());
            log.info("关注的话题发布问题：{}",message);
            rabbitmqProduct.messageTopic(message);

        },this.executor);


        //        4.加入全文检索
        CompletableFuture.runAsync(()->{
            problem.setContent("");//置空，减少网络传输内容
            searchProblem.addES(problem);
            log.info("完成第4步加入全文检索");
        },this.executor);

        return new R(RespBeanEnum.PROBLEM_ENQUIRE_SUCCESS,null);
    }

    @Override
    public R searchProblemByTopic(Long topicId, Long current,Long pageSize) {

        /**先将页数转换为起始记录行**/
        current = (current-1)*pageSize;
        //问题列表
        List<ProblemVo> records = this.problemDao.searchProblemsByTopicId(topicId,current,pageSize);
        //问题总数
        long total = this.problemDao.searchProblemsCountByTopicId(topicId);

        return new R<RespData>(RespBeanEnum.SUCCESS,new RespData(records, total));
    }


    /**
     * 1.增加问题浏览量,直接异步
     * 2.查询问题的所有信息：先查缓存
     * 3.计算问题的实际回答数
     * 4.远程调用：查看当前用户是否关注当前问题
     * @param problemId
     * @return
     */
    @Override
    public ProblemEntity searchProblemById(Long problemId) {

        log.info("进入问题详情业务层方法....");
        UserEntity user = HandlerInterceptor.loginUser.get();
        ProblemEntity problem =  JsonPare.pare( this.redisUtil.get(this.key.getProblem() + ":" + problemId),ProblemEntity.class);
        log.info("problem:{}",problem);
        if (problem == null) {//等于空才去数据库查询
            QueryWrapper<ProblemEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.ne("`condition`",2);
            queryWrapper.eq("id",problemId);
            problem = this.problemDao.selectOne(queryWrapper);
            if (problem == null)
                return null;
        }
        // 1.增加问题浏览量,直接异步
        CompletableFuture.runAsync(()->{
            Double aDouble = this.redisUtil.zIncrementScore(this.key.getVisits(), String.valueOf(problemId));
            log.info("问题{}增加后的分数为：{}",problemId,aDouble);
        },this.executor);
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("problem_id",problemId);
            wrapper.eq("issue",1);
        Long replyLen = this.replyDao.selectCount(wrapper); //回答数量
        if (problem == null)
            return null;
        boolean followProblem = this.userFeign.isFollowProblem(problemId);//远程调用
        problem.setPay(followProblem);
        problem.setReply(replyLen);
        return problem;
    }

    @Override
    public int incrProblemReply(Long problemId) {
        return this.problemDao.incrReplyNumber(problemId);
    }

    @Override
    public R search(String query) {
        List search = this.searchProblem.search(query);
        return new R(RespBeanEnum.SUCCESS,search);
    }

    @Override
    @GlobalTransactional //全局事务管理
    public R followProblem(Long problemId) {

        //先判断问题是否违规
        if (this.checkProblemIsFail(problemId))
            return new R(RespBeanEnum.PROBLEM_FAIL_ERROR,null);
        CollectEntity collect = new CollectEntity();
        UserEntity user = HandlerInterceptor.loginUser.get();
        collect.setUserId(user.getId());
        collect.setProblemId(problemId);

//       问题的关注数量加1
        this.problemDao.incrCollectNumber(collect.getProblemId(),1);

        R insert = this.userFeign.insert(problemId);
        if (insert.getCode() != 200){
            log.info("insert.getCode()为：{}.应该直接报错回滚的",insert.getCode());
            throw new DatabaseException("你已经关注过了");
        }
        CompletableFuture.runAsync(()->{
//            问题有人收藏,消息发给问题得提出者
//            先插入问题标题，id，用户id，用户昵称
            QueryWrapper<ProblemEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("id",problemId);
            wrapper.select("id","title","user_id");
            ProblemEntity problem = problemDao.selectOne(wrapper);
            MessageEntity message = new MessageEntity();

            message.setProblemId(problem.getId());
            message.setProblemTitle(problem.getTitle());
            message.setUserId(problem.getUserId());
            message.setFromUserId(user.getId());//消息来源
            message.setFromUserName(user.getNickName());//消息来源
            message.setCreateTime(new Date());
            message.setKind(5);
            rabbitmqProduct.messageMe(message);
        },this.executor);

        return new R(RespBeanEnum.PROBLEM_COLLECT_SUCCESS,111);
    }

    @Override
    @Transactional
    public boolean updateProblemVisit(Set<ZSetOperations.TypedTuple<Object>> set) {

        try {
            for (ZSetOperations.TypedTuple<Object> problemWithSore : set) {
                this.problemDao.updateProblemVisit(problemWithSore.getValue(),problemWithSore.getScore());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("更新浏览量出错");
        }

        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public R searchHot() {

        List<HotProblem> hotProblems = this.redisUtil.lGet(this.key.getHot(), 0, -1, HotProblem.class);

        return new R(RespBeanEnum.SUCCESS,hotProblems);
    }

    @Override
    public List<ProblemEntity> searchProblems(List<Long> ids) {
        return this.problemDao.searchProblems(ids);
    }

    @Override
    public List<ProblemEntity> searchProblemsByList(List<Long> ids) {
        QueryWrapper<ProblemEntity> wrapper = new QueryWrapper<>();
        wrapper.in("id",ids);
        wrapper.ne("`condition`",2);
         return  this.problemDao.selectList(wrapper);
    }

    @Override
    public List<ProblemEntity> searchProblems(Long userId) {

        QueryWrapper<ProblemEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id","title","create_time","user_name","`condition`","cause");
        wrapper.eq("user_id",userId);
        wrapper.orderByDesc("id");
        List<ProblemEntity> problemEntities = this.problemDao.selectList(wrapper);
        return problemEntities;
    }

    /**
     * 删除问题：已经在数据库层面定义触发器，级联删除了
     * 前提:验证权限通过，确实是问题的发布者才可以删除
     * 1、删除问题记录（利用触发器级联删除了qa数据库所有与该问题相关的记录）
     * 2、删除消息表，所有有关该问题的。远程调用
     * @param problemId
     * @return
     */
    @Override
    public boolean delProblem(Long problemId) {

//        验证权限：当前请求删除问题的用户是不是问题的主人
        UserEntity user = HandlerInterceptor.loginUser.get();
        QueryWrapper<ProblemEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id",problemId);
        wrapper.eq("user_id",user.getId());
        boolean exists = this.problemDao.exists(wrapper);
        if (!exists)
            return false;
        int i = this.problemDao.deleteById(problemId);
        // 原线程的请求信息拷贝被新开的远程调用线程。如果浏览量达到阈值，可以将参数设置多一个，true表示该回答要写入缓存，false表示不写入缓存
        //        获取当前线程的请求信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture.runAsync(()->{
            RequestContextHolder.setRequestAttributes(requestAttributes);
            R r = this.userFeign.delMessageByProblemId(problemId);
            log.info("r:{}",r);
        },this.executor);
        CompletableFuture.runAsync(()->{
            boolean del = searchProblem.del(problemId);
            log.info("ES删除结果:{}",del);
        },this.executor);
        return true;
    }

    @Override
    public List<ProblemEntity> searchFollowProblems() {

        List<Long> problemIds = this.userFeign.searchFollowProblems();
        QueryWrapper<ProblemEntity> wrapper = new QueryWrapper<>();
        if(problemIds.size() <=0)
            return new ArrayList<>();//返回一个空的
        wrapper.in("id",problemIds);
        wrapper.select("id","title","create_time","user_name");
        wrapper.ne("`condition`",2);
        List<ProblemEntity> problems = this.problemDao.selectList(wrapper);
        return problems;
    }

    @Override
    public List<ProblemEntity> difference(List list, Integer number) {

        return this.problemDao.differenceProblems(list,number);

    }

    @Override
    public boolean checkProblemIsFail(Long problemId) {
        return this.problemDao.checkProblemIsFail(problemId)==2;
    }


}