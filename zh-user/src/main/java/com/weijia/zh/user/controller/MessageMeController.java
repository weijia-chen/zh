package com.weijia.zh.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.entity.MessageFollowEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.user.entity.MessageMeEntity;
import com.weijia.zh.user.service.MessageMeService;

import javax.servlet.http.HttpSession;


/**
 *
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@RestController
@RequestMapping("/messageme")
public class MessageMeController {

    @Autowired
    private MessageMeService messageMeService;

    @GetMapping("/searchMessageMe/{current}")
    public R searchMessageMe(@PathVariable("current") Integer current) {
        return messageMeService.searchMessageMe(current);
    }
}
