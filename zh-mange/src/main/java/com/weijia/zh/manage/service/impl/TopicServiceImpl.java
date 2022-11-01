package com.weijia.zh.manage.service.impl;

import com.weijia.zh.manage.dao.TopicDao;
import com.weijia.zh.manage.entity.Topic;
import com.weijia.zh.manage.exception.DatabaseException;
import com.weijia.zh.manage.service.TopicService;
import com.weijia.zh.manage.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @Resource
    private TopicDao topicDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Topic> searchTopics() {
        return this.topicDao.selectList(null);
    }

    @Override
    public boolean updateTopicByInr(Topic topic) {

        this.delTopicsFromRedis();
        return this.topicDao.updateById(topic)>0;
    }

    @Override
    public Topic insertTopic(Topic topic) {
        try {
            this.topicDao.insert(topic);
            this.delTopicsFromRedis();
            return topic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("话题名称不可以重复");
        }
    }

    /**删除话题缓存*/
    public void delTopicsFromRedis(){
        this.redisUtil.del("topics");
    }
}
