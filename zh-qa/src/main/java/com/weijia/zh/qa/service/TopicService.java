package com.weijia.zh.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.entity.TopicEntity;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
public interface TopicService{

    R<List<TopicEntity>> searchTopicsWithUser();

    List<TopicEntity> searchTopics();
}

