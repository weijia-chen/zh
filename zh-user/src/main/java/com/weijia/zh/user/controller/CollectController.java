package com.weijia.zh.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weijia.zh.common.entity.ProblemEntity;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.config.HandlerInterceptor;
import com.weijia.zh.user.entity.UserEntity;
import com.weijia.zh.user.service.FollowService;
import com.weijia.zh.user.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.user.entity.CollectEntity;
import com.weijia.zh.user.service.CollectService;

import javax.servlet.http.HttpSession;


/**
 * 关注问题
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@Slf4j
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;

    @Autowired
    private FollowService followService;

    /**
     * 关注问题，写入数据库
     */
    @PostMapping("/insert")
    public R insert(@RequestBody Long problemId){

        return this.collectService.insertFollowProblem(problemId);
    }

    /**
     * 查找用户关注的所有问题
     * @return
     */
    @GetMapping("/searchProblems")
    public R  searchProblems(){
        UserEntity user = HandlerInterceptor.loginUser.get();
        List<ProblemEntity> problemEntities = this.collectService.searchProblems(user.getId());
        return new R(RespBeanEnum.SUCCESS,problemEntities);
    }


}
