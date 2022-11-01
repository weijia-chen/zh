package com.weijia.zh.user.controller;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.service.MessageManageService;
import com.weijia.zh.user.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/messageManage")
public class MessageManageController {

    @Autowired
    private MessageManageService messageManageService;

    @GetMapping("/delMessageByProblemId")
    public R delMessageByProblemId(Long problemId){

        log.info("delMessageByProblemId,problemId:{}",problemId);
        boolean b = this.messageManageService.delMessageByProblemId(problemId);
        if (b)
            return new R(RespBeanEnum.SUCCESS,null);
        return new R(RespBeanEnum.ERROR,null);
    }
    @GetMapping("/delMessageByReplyId")
    public R delMessageByReplyId(Long replyId){

        log.info("delMessageByReplyId,replyId:{}",replyId);
        boolean b = this.messageManageService.delMessageByReplyId(replyId);
        if (b)
            return new R(RespBeanEnum.SUCCESS,null);
        return new R(RespBeanEnum.ERROR,null);
    }

}
