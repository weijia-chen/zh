package com.weijia.zh.manage.controller;

import com.weijia.zh.manage.entity.Topic;
import com.weijia.zh.manage.service.TopicService;
import com.weijia.zh.manage.vo.R;
import com.weijia.zh.manage.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public R searchTopics(){
        return new R(RespBeanEnum.SUCCESS,this.topicService.searchTopics());
    }

    @PostMapping("/updateTopicByInr")
    public R updateTopicByInr(@RequestBody Topic topic){
        boolean b = this.topicService.updateTopicByInr(topic);
        if (b)
            return new R(RespBeanEnum.SUCCESS,null);
        return new R(RespBeanEnum.ERROR,null);
    }

    @PostMapping("/insertTopic")
    public R insertTopic(@RequestBody Topic topic){
        this.topicService.insertTopic(topic);
        return new R(RespBeanEnum.SUCCESS,topic);
    }

}
