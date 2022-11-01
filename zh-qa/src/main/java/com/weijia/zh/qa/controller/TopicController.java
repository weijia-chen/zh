package com.weijia.zh.qa.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.qa.entity.TopicEntity;
import com.weijia.zh.qa.service.TopicService;

import javax.servlet.http.HttpSession;


/**
 * 
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;


    @GetMapping("/searchTopics")
    public R<List<TopicEntity>> searchTopics(){
        return topicService.searchTopicsWithUser();
    }

    @GetMapping("/searchTopics02")
    public List<TopicEntity>searchTopics02(){
        return this.topicService.searchTopics();
    }


}
