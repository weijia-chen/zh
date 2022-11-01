package com.weijia.zh.qa.service.impl;

import com.weijia.zh.common.entity.UserEntity;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.config.HandlerInterceptor;
import com.weijia.zh.qa.exception.DatabaseException;
import com.weijia.zh.qa.feign.UserFeign;
import com.weijia.zh.qa.key.Key;
import com.weijia.zh.qa.utils.RedisUtil;
import com.weijia.zh.qa.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.weijia.zh.qa.dao.TopicDao;
import com.weijia.zh.qa.entity.TopicEntity;
import com.weijia.zh.qa.service.TopicService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@Service("topicService")
public class TopicServiceImpl  implements TopicService {


    @Resource
    private Key key;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    @Qualifier("threadPoolTaskExecutor")//指定注入bean的名称
    private ThreadPoolTaskExecutor executor;

    /**
     * 先去缓存查，没有再去数据库查
     * 使用分布式锁redisson 解决缓存击穿问题（话题缓存只可能存在缓存击穿问题，没有雪崩和穿透问题）
     * @return
     */
    @Override
    public R<List<TopicEntity>> searchTopicsWithUser() {

        UserEntity user = HandlerInterceptor.loginUser.get();

//        获取当前线程的请求信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        CompletableFuture<List<Long>> future = CompletableFuture.supplyAsync(() -> {
//            异步远程调用， 原线程的请求信息拷贝被新开的远程调用线程
            RequestContextHolder.setRequestAttributes(requestAttributes);
            log.info("远程调用feign");
            return this.userFeign.searchUserFollowTopics(user.getId());
        }, this.executor);

        List<TopicEntity> topics = searchTopics();

        /**
         * 将话题中用户已经关注的标记一下
         */
        List<Long> longs;

        try {
            longs = future.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new DatabaseException("网络中断");
        }
        if (longs==null)
            throw  new DatabaseException("网络中断");

        log.info("得到的话题id，{}",longs);
        for (TopicEntity topic : topics) {
            if(longs.contains(topic.getId()))
                topic.setPay(true);
        }

        return new R<List<TopicEntity>>(RespBeanEnum.SUCCESS,topics);
    }

    @Override
    public List<TopicEntity> searchTopics() {

        List<TopicEntity> topics = redisUtil.lGet(this.key.getTopics(), 0, -1, TopicEntity.class);
        if (topics == null || topics.size() <=0){
            RLock lock = redissonClient.getLock(this.key.getDtopics()); //得到分布式锁
            log.info("得到分布式锁");
            lock.lock(10, TimeUnit.SECONDS);//10s后自动释放锁
            try {
                topics = redisUtil.lGet(this.key.getTopics(), 0, -1, TopicEntity.class);//加锁后再次查找
                if (topics == null || topics.size() <=0){//如果还是为空，就去访问数据库。防止有多个线程去访问数据库
                    log.info("查询数据库");
                    topics = topicDao.selectList(null);
                    this.redisUtil.lSet(this.key.getTopics(),topics,60*60*12); //写入缓存,过期时间模拟修改了话题
                }
            }finally {
                lock.unlock();
            }
        }
        return topics;
    }
}