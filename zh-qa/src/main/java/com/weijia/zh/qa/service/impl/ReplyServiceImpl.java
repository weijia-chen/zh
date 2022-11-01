package com.weijia.zh.qa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijia.zh.common.entity.MessageEntity;
import com.weijia.zh.common.entity.MessageMeEntity;
import com.weijia.zh.common.entity.UserEntity;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.config.HandlerInterceptor;
import com.weijia.zh.qa.dao.CollectDao;
import com.weijia.zh.qa.dao.ProblemDao;
import com.weijia.zh.qa.entity.Collect;
import com.weijia.zh.qa.entity.ProblemEntity;
import com.weijia.zh.qa.exception.DatabaseException;
import com.weijia.zh.qa.feign.UserFeign;
import com.weijia.zh.qa.key.Key;
import com.weijia.zh.qa.service.CollectReplyService;
import com.weijia.zh.qa.service.ProblemService;
import com.weijia.zh.qa.service.RabbitmqProduct;
import com.weijia.zh.qa.utils.RedisUtil;
import com.weijia.zh.qa.vo.HotProblem;
import com.weijia.zh.qa.vo.ReplyVo;
import com.weijia.zh.qa.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.weijia.zh.qa.dao.ReplyDao;
import com.weijia.zh.qa.entity.ReplyEntity;
import com.weijia.zh.qa.service.ReplyService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;

@Slf4j
@Service("replyService")
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private Key key;

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private CollectReplyService collectReplyService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    @Lazy //延迟加载解决循环依赖问题
    private ProblemService problemService;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    @Qualifier("threadPoolTaskExecutor")//指定注入bean的名称
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private RabbitmqProduct rabbitmqProduct;

    /**
     * 查询指定回答:
     * 1.答主是否关注
     * 2.回答是否收藏
     * 3.回答是否点赞
     * 4.回答的收藏数量
     * @param problemId
     * @param current
     * @return
     */
    @Override
    public ReplyEntity searchReply(Long problemId,Long current) {

        UserEntity user = HandlerInterceptor.loginUser.get();
        ReplyEntity replyEntity =  this.replyDao.searchReplyByProblemId(problemId,current-1);
        if (replyEntity == null)
            return null;
        Long userId = replyEntity.getUserId();//答主id
        Long replyId = replyEntity.getId();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture<Boolean> isFollow = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            return this.userFeign.isFollow(userId);//远程调用:判断答主是否被关注
        }, this.executor);

        CompletableFuture<Boolean> isCollect = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            QueryWrapper<Collect> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("user_id", user.getId());
            wrapper1.eq("reply_id", replyId);
            return this.collectDao.exists(wrapper1);//是否被收藏
        }, this.executor);
        // 判断该人是否给该回答点赞过了
        CompletableFuture<Boolean> isPraise = CompletableFuture.supplyAsync(() -> {
            return this.redisUtil.sHasKey(this.key.getPraiseUser() + ":" + replyId, userId);
        }, this.executor);

        CompletableFuture<Long> praiseSize = CompletableFuture.supplyAsync(() -> {
            return this.redisUtil.sGetSetSize(this.key.getPraiseUser() + ":" + replyId);
        }, this.executor);

        CompletableFuture.allOf(isFollow,isCollect,isPraise,praiseSize);
        try {
            replyEntity.setPay(isFollow.get()); //是否关注
            replyEntity.setIsCollect(isCollect.get()); //是否收藏
            replyEntity.setIsPraise(isPraise.get()); //是否点赞
            replyEntity.setThumbUp(praiseSize.get());//点赞数量
        } catch (Exception e) {
            e.printStackTrace();
        }

//        回答的收藏数量
        replyEntity.setCollect(this.collectReplyService.countCollectReply(replyId));
        return replyEntity;
    }


    /**
     * 通过问题查询回答,并标记用户关注过的作者：
     * 1、先查缓存
     * 2、缓存没有再查数据库
     * 3、无需将查询结果写入缓存（所以也没必要加锁）
     * @param id
     * @return
     */
    @Override
    @Deprecated
    public List<ReplyEntity> searchReplyById(Long id,Long userId) {

        // 获取当前线程的请求信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture<List<Long>> future = CompletableFuture.supplyAsync(() -> {
            //            异步远程调用， 原线程的请求信息拷贝被新开的远程调用线程
            RequestContextHolder.setRequestAttributes(requestAttributes);
            return this.userFeign.searchUserFollowUsers(userId);
        }, this.executor);

        List<ReplyEntity> replyEntities = this.redisUtil.lGet(this.key.getReplies()+":"+id,0,-1,ReplyEntity.class);
        log.info("通过缓存查询的replyEntities:{}",replyEntities);

        //        如果replyEntities的长度为1，就判断是否为空值
        if (  replyEntities.size() == 1 && !(replyEntities.get(0) instanceof ReplyEntity))
            return new ArrayList<ReplyEntity>();

        if (replyEntities == null || replyEntities.size() <= 0){
            QueryWrapper<ReplyEntity> wrapper = new QueryWrapper();
            wrapper.eq("problem_id",id);
            wrapper.orderByDesc("update_time");
            replyEntities = this.replyDao.selectList(wrapper);
        }

        List<Long> longs;
        try {
            longs = future.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("网络中断");
        }
        if (longs == null)
            throw new DatabaseException("未知异常");

//        标记关注的人发布的回答
        for (ReplyEntity reply : replyEntities) {
            if (longs.contains(reply.getUserId()))
                reply.setPay(true);
        }
        return replyEntities;

    }



    /**
     *更新回答点赞次数
     */
    @Override
    public int updateReplyLike(Long replyId, Long number) {
        return this.replyDao.incrReplyLike(replyId,number);
    }

    /**
     * 添加回答
     * 写入mq，交由进行消息写入
     * @param reply
     * @return
     */
    @Override
    public R insertReply(ReplyEntity reply) throws DatabaseException {

        //先判断问题是否违规
        if (this.problemService.checkProblemIsFail(reply.getProblemId()))
            return new R(RespBeanEnum.PROBLEM_FAIL_ERROR,null);
//        回答数量由查询问题详情时聚合函数查询得到
        UserEntity user = HandlerInterceptor.loginUser.get();
        reply.setCreateTime(new Date());
        reply.setUpdateTime(new Date());
        reply.setUserAvatar(user.getAvatar());
        reply.setUserId(user.getId());
        reply.setUserName(user.getNickName());

        int insert = 0;//插入数据库
        try {
            reply.setThumbUp(0L);
            insert = this.replyDao.insert(reply);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("你已经回答过了");
        }
        if (insert<=0)
            new R(RespBeanEnum.REPLY_ERROR,null);

//       如果是存为草稿不用发通知
        if (reply.getIssue() == 0)
            return new R(RespBeanEnum.REPLY_SUCCESS,reply);//reply用于回显

        CompletableFuture.runAsync(()->{
//            1.查询问题的标题，和问题的提出者
            QueryWrapper<ProblemEntity> wrapper = new QueryWrapper<>();
            wrapper.select("title","user_id","id");
            wrapper.eq("id",reply.getProblemId());
            ProblemEntity problem = problemDao.selectOne(wrapper);

//            2.构造基本的消息对象
            MessageEntity message = new MessageEntity();
            message.setProblemId(problem.getId()); //问题id
            message.setProblemTitle(problem.getTitle());//问题标题
            message.setFromUserId(user.getId()); //来源用户
            message.setFromUserName(user.getNickName());//来源用户昵称
            message.setCreateTime(new Date());//创建时间
            message.setReplyId(reply.getId());//回答id

//            2.1 发送给关注的人回答问题的表：发送给谁得在关注表查询,根据来源用户id
            message.setKind(1);//消息类型为1
            log.info("发送给关注的人回答问题的表：{}",message);
            rabbitmqProduct.messageFollow(message);
//            2.2发给关注了问题的所有人
            rabbitmqProduct.messageProblem(message);
            log.info("发送给问题的关注者：{}",message);
//            2.3发送给问题的提出者
            message.setUserId(problem.getUserId());
            message.setKind(4);//消息类型改变，由于这个CompletableFuture中是单线程，所以不会影响2.1的kind
            log.info("发送给问题的提出者：{}",message);
            rabbitmqProduct.messageMe(message);
        },this.executor);

        return new R(RespBeanEnum.REPLY_SUCCESS,reply);//reply用于回显
    }

    @Override
    public List<ReplyVo> searchReplyVo(Long userId) {
        return this.replyDao.searchReplies(userId,1);
    }

    /**
     * 验证权限后删除
     * @param replyId
     * @return
     */
    @Override
    public boolean delReply(Long replyId) {
        //        验证权限：当前请求删除回答的用户是不是回答的主人
        UserEntity user = HandlerInterceptor.loginUser.get();
        QueryWrapper<ReplyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id",replyId);
        wrapper.eq("user_id",user.getId());
        boolean exists = this.replyDao.exists(wrapper);
        log.info("exists:{}",exists);
        if (!exists)
            return false;
        int i = this.replyDao.deleteById(replyId);
        // 原线程的请求信息拷贝被新开的远程调用线程。如果浏览量达到阈值，可以将参数设置多一个，true表示该回答要写入缓存，false表示不写入缓存
        //        获取当前线程的请求信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture.runAsync(()->{
            RequestContextHolder.setRequestAttributes(requestAttributes);
            this.userFeign.delMessageByReplyId(replyId);
        },this.executor);

        return true;
    }

    @Override
    public List<ReplyVo> searchDrafts() {
        UserEntity user = HandlerInterceptor.loginUser.get();
        return this.replyDao.searchReplies(user.getId(), 0);
    }

    @Override
    public boolean updateDrafts(ReplyEntity reply1) {
        int i = 0;
        reply1.setUpdateTime(new Date());
        ReplyEntity reply;
        try {
            i = this.replyDao.updateById(reply1);
            reply = this.replyDao.selectById(reply1.getId());//查询完整记录
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (i<=0)
            return false;

        if (reply1.getIssue() == 0)//如果是草稿就结束了
            return true;

        //   如果是发布回答，需要发通知
        CompletableFuture.runAsync(()->{
//            1.查询问题的标题，和问题的提出者
            QueryWrapper<ProblemEntity> wrapper = new QueryWrapper<>();
            wrapper.select("title","user_id","id");
            wrapper.eq("id",reply.getProblemId());
            ProblemEntity problem = problemDao.selectOne(wrapper);

//            2.构造基本的消息对象
            MessageEntity message = new MessageEntity();
            message.setProblemId(problem.getId()); //问题id
            message.setProblemTitle(problem.getTitle());//问题标题
            message.setFromUserId(reply.getUserId()); //来源用户
            message.setFromUserName(reply.getUserName());//来源用户昵称
            message.setCreateTime(new Date());//创建时间
            message.setReplyId(reply.getId());//回答id

//            2.1 发送给关注的人回答问题的表：发送给谁得在关注表查询,根据来源用户id
            message.setKind(1);//消息类型为1
            log.info("发送给关注的人回答问题的表：{}",message);
            rabbitmqProduct.messageFollow(message);
//            2.2发给关注了问题的所有人
            rabbitmqProduct.messageProblem(message);
            log.info("发送给问题的关注者：{}",message);
//            2.3发送给问题的提出者
            message.setUserId(problem.getUserId());
            message.setKind(4);//消息类型改变，由于这个CompletableFuture中是单线程，所以不会影响2.1的kind
            log.info("发送给问题的提出者：{}",message);
            rabbitmqProduct.messageMe(message);
        },this.executor);

        return true;
    }
    /**
     * 查询指定回答:
     * 1.答主是否关注
     * 2.回答是否收藏
     * 3.回答是否点赞
     * 4.回答的收藏数量
     * @param replyId
     * @return
     */
    @Override
    public ReplyEntity searchReplyByReplyId(Long replyId) {

        UserEntity user = HandlerInterceptor.loginUser.get();
        ReplyEntity replyEntity = this.replyDao.selectById(replyId);
        log.info("查询得到replyEntity:{}",replyEntity);
        Long userId = replyEntity.getUserId();//答主id
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture<Boolean> isFollow = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            return this.userFeign.isFollow(userId);//远程调用:判断答主是否被关注
        }, this.executor);

        CompletableFuture<Boolean> isCollect = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            QueryWrapper<Collect> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("user_id", user.getId());
            wrapper1.eq("reply_id", replyId);
            return this.collectDao.exists(wrapper1);//是否被收藏
        }, this.executor);
        // 判断该人是否给该回答点赞过了
        CompletableFuture<Boolean> isPraise = CompletableFuture.supplyAsync(() -> {
            return this.redisUtil.sHasKey(this.key.getPraiseUser() + ":" + replyId, userId);
        }, this.executor);

        CompletableFuture<Long> praiseSize = CompletableFuture.supplyAsync(() -> {
            return this.redisUtil.sGetSetSize(this.key.getPraiseUser() + ":" + replyId);
        }, this.executor);

        CompletableFuture.allOf(isFollow,isCollect,isPraise,praiseSize);
        try {
            replyEntity.setPay(isFollow.get()); //是否关注
            replyEntity.setIsCollect(isCollect.get()); //是否收藏
            replyEntity.setIsPraise(isPraise.get()); //是否点赞
            replyEntity.setThumbUp(praiseSize.get());//点赞数量
        } catch (Exception e) {
            e.printStackTrace();
        }

//        回答的收藏数量
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("reply_id",replyId);
        Long aLong = this.collectDao.selectCount(wrapper);
        replyEntity.setCollect(aLong);

        return replyEntity;
    }
    /**
     * 根据指定问题查询回答
     * @param id
     * @return
     */
    @Override
    @Deprecated
    public List<ReplyEntity> searchReplyById(Long id) {

        QueryWrapper<ReplyEntity> wrapper = new QueryWrapper();
        wrapper.eq("problem_id",id);
        wrapper.orderByDesc("update_time");
        List<ReplyEntity> replyEntities = this.replyDao.selectList(wrapper);
        return replyEntities;
    }

    /**
     * 查询指定问题的5条精选回答：点赞最多的5(this.key.getHotNumber())条回答
     * @return
     */
    @Override
    @Deprecated
    public List<ReplyEntity> searchHotRepliesById(List<ReplyEntity> replyEntities) {

        return replyEntities.stream()
                .sorted(Comparator.comparing(ReplyEntity::getThumbUp)
                        .reversed()).limit(this.key.getHotNumber()).collect(Collectors.toList());

    }

}