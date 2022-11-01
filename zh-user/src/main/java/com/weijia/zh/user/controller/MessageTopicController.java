package com.weijia.zh.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weijia.zh.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.user.entity.MessageTopicEntity;
import com.weijia.zh.user.service.MessageTopicService;

import javax.servlet.http.HttpSession;


/**
 * 
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 14:00:41
 */
@RestController
@RequestMapping("/messagetopic")
public class MessageTopicController {

    @Autowired
    private MessageTopicService messageTopicService;

    @GetMapping("/searchMessageTopics/{current}")
   public R  searchMessageTopics(@PathVariable("current") Integer current){

        return messageTopicService.searchMessageTopics(current);
    }
}
