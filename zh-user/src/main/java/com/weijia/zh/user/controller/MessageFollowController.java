package com.weijia.zh.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.entity.MessageMeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.user.entity.MessageFollowEntity;
import com.weijia.zh.user.service.MessageFollowService;

import javax.servlet.http.HttpSession;


/**
 *
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@RestController
@RequestMapping("/messageFollow")
@Slf4j
public class MessageFollowController {

    @Autowired
    private MessageFollowService messageFollowService;

    /**
     * 查出关注博主的动态
     * @param current :页数,分页查询,第current页
     * @return
     */
    @GetMapping("/searchMessageFollow/{current}")
    public R searchMessageFollow(@PathVariable("current") Integer current){
        return messageFollowService.searchMessageFollowByIdList(current);
    }


}
