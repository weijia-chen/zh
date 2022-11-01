package com.weijia.zh.user.service.impl;

import com.weijia.zh.common.entity.TopicEntity;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.config.HandlerInterceptor;
import com.weijia.zh.user.dao.*;
import com.weijia.zh.user.entity.*;
import com.weijia.zh.user.exception.DatabaseException;
import com.weijia.zh.user.feign.QaFeign;
import com.weijia.zh.user.service.MessageFollowService;
import com.weijia.zh.user.utils.RedisUtil;
import com.weijia.zh.user.vo.FollowUserInfoVo;
import com.weijia.zh.user.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.weijia.zh.user.service.FollowService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Slf4j
@Service("followService")
public class FollowServiceImpl  implements FollowService {

    @Autowired
    private FollowDao followDao;

    @Autowired
    private FollowTopicDao followTopicDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FollowProblemDao followProblemDao;

    @Autowired
    @Qualifier("threadPoolTaskExecutor")//指定注入bean的名称
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private QaFeign qaFeign;

    @Autowired
    private MessageFollowDao messageFollowDao;

    @Autowired
    private MessageMeDao messageMeDao;

    @Autowired
    private MessageTopicDao messageTopicDao;

    @Autowired
    private MessageProblemDao messageProblemDao;

    @Override
    public R followTopic( Long topicId) {

        UserEntity user = HandlerInterceptor.loginUser.get();
        FollowTopic followTopic = new FollowTopic();
        followTopic.setUserId(user.getId());
        followTopic.setTopicId(topicId);
        int insert = 0;
        try {
            insert = this.followTopicDao.insert(followTopic);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("你已经关注过该话题了");
        }

        if (insert<=0)
            throw new DatabaseException("你已经关注过该话题了");

        return new R(RespBeanEnum.SUCCESS,111);
    }

    /**
     * 用户的关注人数加1
     * 被关注人的粉丝数加1
     * @param userId
     * @return
     */
    @Override
    @Transactional //本地事务
    public R followUser(Long userId) {

        UserEntity user = HandlerInterceptor.loginUser.get();
        log.info("从ThreadLocal拿到的用户信息：{}",user);
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUserId(user.getId());
        followEntity.setTargetId(userId);

        int insert = 0;
        try {
            insert = this.followDao.insert(followEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("你已经关注过该用户了");
        }

        if (insert<=0)
            throw new DatabaseException("你已经关注过该用户了");

        this.userDao.incrFollowCount(user.getId(),1);
        this.userDao.incrFansCount(userId,1);

        CompletableFuture.runAsync(()->{

            MessageMeEntity messageMeEntity = new MessageMeEntity();
            messageMeEntity.setKind(3);
            messageMeEntity.setCreateTime(new Date());
            messageMeEntity.setFromUserId(user.getId());
            messageMeEntity.setFromUserName(user.getNickName());
            messageMeEntity.setUserId(userId);

//            投递到队列
            log.info("投递到队列:{}",messageMeEntity);
            rabbitTemplate.convertAndSend("message","message_me",messageMeEntity);

        },this.executor);

        return new R(RespBeanEnum.SUCCESS,111);

    }


    @Override
    public List<Long> searchUserFollowTopics(Long userId) {
        return this.followTopicDao.searchUserFollowTopics(userId);
    }

    @Override
    public List<Long> searchUserFollowUsers(Long userId) {
        return this.followDao.searchUserFollowUsers(userId);
    }

    @Override
    public List<FollowUserInfoVo> searchFollowUsers(Long userId) {
        return this.followDao.searchFollow(userId);
    }

    @Override
    public List<FollowTopic> searchFollowTopics(Long userId) {

//        查询用户关注的话题id
       QueryWrapper<FollowTopic> wrapper = new QueryWrapper<>();
       wrapper.eq("user_id",userId);
       List<FollowTopic> followTopics = this.followTopicDao.selectList(wrapper);

//       通过话题id查询话题名称
        List<TopicEntity> topics = this.qaFeign.searchTopics02();
        log.info("得到所有话题:{}",topics);

//        注入话题名称
        for (FollowTopic followTopic : followTopics) {

            Iterator<TopicEntity> iterator = topics.iterator();
            while (iterator.hasNext()){

                TopicEntity next = iterator.next();
                if(next.getId().equals(followTopic.getTopicId())){
                    followTopic.setTopicName(next.getTopicName());
                    break;
                }
            }
        }
        return followTopics;
    }

    /**
     * 取关博主
     * 1、删除关注关系
     * 2、删除该博主给当前用户产生的所有消息，都在tb_message_follow表中
     * @param userId
     * @return
     */
    @Override
    public boolean delFollowUser(Long userId) {

        UserEntity user = HandlerInterceptor.loginUser.get();
        try {
            QueryWrapper<FollowEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",user.getId());
            wrapper.eq("target_id",userId);
            this.followDao.delete(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
        // 用户关注数减1,博主粉丝数也要减1
        CompletableFuture.runAsync(()->{
            userDao.incrFansCount(userId,-1); //userId是博主id
            userDao.incrFollowCount(user.getId(),-1);//user是当前取关操作的用户
            QueryWrapper<MessageFollowEntity> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("from_user_id",userId); //消息来源用户
            wrapper1.eq("user_id",user.getId());//消息接收用户
            this.messageFollowDao.delete(wrapper1);
        },this.executor);

        return true;
    }

    /**
     * 取关问题
     * 1、关注关系删除
     * 2、关注问题消息表中：接收为当前用户的和来源问题为指定问题的全部删除
     * @param problemId
     * @return
     */
    @Override
    public boolean delFollowProblem(Long problemId) {
        UserEntity user = HandlerInterceptor.loginUser.get();
        try {
            QueryWrapper<FollowProblem> wrapper = new QueryWrapper();
            wrapper.eq("user_id",user.getId());
            wrapper.eq("problem_id",problemId);
            this.followProblemDao.delete(wrapper);

            qaFeign.dcrProblemFollow(problemId);
            QueryWrapper<MessageProblem> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("user_id",user.getId());
            wrapper1.eq("problem_id",problemId);
            this.messageProblemDao.delete(wrapper1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 取关话题。同理
     * @param topicId
     * @return
     */
    @Override
    public boolean delFollowTopic(Long topicId) {

        try {
            UserEntity user = HandlerInterceptor.loginUser.get();
            QueryWrapper<FollowTopic> wrapper = new QueryWrapper();
            wrapper.eq("user_id",user.getId());
            wrapper.eq("topic_id",topicId);
            this.followTopicDao.delete(wrapper);

            QueryWrapper<MessageTopicEntity> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("user_id",user.getId());
            wrapper1.eq("topic_id",topicId);
            this.messageTopicDao.delete(wrapper1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isFollow(Long targetId) {
        UserEntity user = HandlerInterceptor.loginUser.get();
        QueryWrapper<FollowEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());
        wrapper.eq("target_id",targetId);
        return this.followDao.exists(wrapper);
    }

    @Override
    public boolean isFollowProblem(Long problemId) {
        UserEntity user = HandlerInterceptor.loginUser.get();
        QueryWrapper<FollowProblem> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());
        wrapper.eq("problem_id",problemId);
        return this.followProblemDao.exists(wrapper);

    }

    @Override
    public List<Long> searchFollowProblems(Long userId) {
        return  this.followProblemDao.searchProblemIds(userId);
    }

    @Override
    public boolean delFollowByProblem(Long problemId) {

        QueryWrapper<FollowProblem> wrapper = new QueryWrapper<>();
        wrapper.eq("problem_id",problemId);
        return  this.followProblemDao.delete(wrapper)>0;
    }
}