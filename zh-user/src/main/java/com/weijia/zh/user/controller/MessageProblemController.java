package com.weijia.zh.user.controller;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.service.MessageProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messageProblem")
@Slf4j
public class MessageProblemController {

    @Autowired
    private MessageProblemService messageProblemService;

    /**
     * 分页查询用户关注问题的动态
     * 注意:每页数是固定的. 为8条记录
     * @param current 第current页.
     * @return
     */
    @GetMapping("/searchMessageProblems/{current}")
    public R searchMessageProblems(@PathVariable("current")Integer current){
        return this.messageProblemService.searchMessageProblems(current);
    }
}
