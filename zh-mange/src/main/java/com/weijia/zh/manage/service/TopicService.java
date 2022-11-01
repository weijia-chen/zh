package com.weijia.zh.manage.service;


import com.weijia.zh.manage.entity.Topic;

import java.util.List;

/**
 * 
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
public interface TopicService {

    List<Topic> searchTopics();

    boolean updateTopicByInr(Topic topic);

    Topic insertTopic(Topic topic);
}

