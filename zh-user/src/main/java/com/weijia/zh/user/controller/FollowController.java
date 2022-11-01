package com.weijia.zh.user.controller;

import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.config.HandlerInterceptor;
import com.weijia.zh.user.entity.FollowTopic;
import com.weijia.zh.user.entity.UserEntity;
import com.weijia.zh.user.vo.FollowUserInfoVo;
import com.weijia.zh.user.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.user.service.FollowService;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 *
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@Slf4j
@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;


    @GetMapping("/followTopic/{topicId}")
    public R followTopic(@PathVariable("topicId")Long topicId){
        return this.followService.followTopic(topicId);
    }

    /**关注用户**/
    @GetMapping("/followUser/{userId}")
    public R followUser(@PathVariable("userId")Long userId){
        return this.followService.followUser(userId);
    }

    @GetMapping("/searchUserFollowTopics")
    public List<Long> searchUserFollowTopics(@RequestParam("userId") Long userId){
        return this.followService.searchUserFollowTopics(userId);
    }

    @GetMapping("/searchUserFollowUsers")
    public List<Long> searchUserFollowUsers(@RequestParam("userId") Long userId){
        return this.followService.searchUserFollowUsers(userId);
    }
    /**关注问题*/
    @GetMapping("/searchFollowProblems")
    public List<Long> searchFollowProblems(){
        log.info("searchFollowProblems");
        UserEntity user = HandlerInterceptor.loginUser.get();
        return this.followService.searchFollowProblems(user.getId());
    }
    /**
     * 当前用户是否关注targetId
     * @param targetId 被关注的用户
     * @return
     */
    @GetMapping("/isFollow")
    public boolean isFollow(@RequestParam("targetId") Long targetId){
        return this.followService.isFollow(targetId);
    }

    /**
     * 是否关注问题
     * @param problemId 被关在的问题
     * @return
     */
    @GetMapping("/isFollowProblem")
    public boolean isFollowProblem(@RequestParam("problemId") Long problemId){
        return this.followService.isFollowProblem(problemId);
    }

    @GetMapping("/searchFollowUsers")
    public R searchFollowUsers(){
        UserEntity user = HandlerInterceptor.loginUser.get();
        List<FollowUserInfoVo> followUserInfoVos = this.followService.searchFollowUsers(user.getId());
        return new R(RespBeanEnum.SUCCESS,followUserInfoVos);
    }
    @GetMapping("/searchFollowTopics")
    public R searchFollowTopics(){
        UserEntity user = HandlerInterceptor.loginUser.get();
        List<FollowTopic> followTopics = this.followService.searchFollowTopics(user.getId());
        return new R(RespBeanEnum.SUCCESS,followTopics);
    }
    /**
     * 取消关注博主
     * @param userId 被取关的用户id，即取关的博主id。自己的id从cookie获得无需关注
     * @return
     */
    @GetMapping("/delFollowUser/{userId}")
    public R delFollowUser(@PathVariable("userId")Long userId){
        boolean b = this.followService.delFollowUser(userId);
        if (b)
            return new R(RespBeanEnum.DEL_FOLLOW_SUCCESS,null);
        return new R(RespBeanEnum.DEL_FOLLOW_ERROR,null);
    }

    /**
     * 取消关注问题
     * @param problemId 问题id
     * @return
     */
    @GetMapping("/delFollowProblem/{problemId}")
    public R delFollowProblem(@PathVariable("problemId")Long problemId){
        boolean b = this.followService.delFollowProblem(problemId);
        if (b)
            return new R(RespBeanEnum.DEL_FOLLOW_SUCCESS,null);
        return new R(RespBeanEnum.DEL_FOLLOW_ERROR,null);
    }

    /**
     * 取消关注话题
     * @param topicId 话题id
     * @return
     */
    @GetMapping("/delFollowTopic/{topicId}")
    public R delFollowTopic(@PathVariable("topicId")Long topicId){
        boolean b = this.followService.delFollowTopic(topicId);
        if (b)
            return new R(RespBeanEnum.DEL_FOLLOW_SUCCESS,null);
        return new R(RespBeanEnum.DEL_FOLLOW_ERROR,null);
    }

    @GetMapping("/delFollowByProblem")
    public Boolean delFollowByProblem(@RequestParam("problemId")Long problemId){
        return this.followService.delFollowByProblem(problemId);
    }

}
