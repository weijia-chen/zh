package com.weijia.zh.user.controller;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.config.HandlerInterceptor;
import com.weijia.zh.user.feign.QaFeign;
import com.weijia.zh.user.service.UserService;
import com.weijia.zh.user.vo.Login;
import com.weijia.zh.user.vo.Register;
import com.weijia.zh.user.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.user.entity.UserEntity;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;


/**
 * 用户
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Validated //在controller层直接验证参数，必须加上这个注解
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value="threadPoolTaskExecutor") //想要@Autowired 的byName注入就要利用 @Qualifier 来指定
    private ThreadPoolTaskExecutor executor;


    /**
     * 注册
     * @param uploadFile ：头像文件
     * @return
     */
    @PostMapping("/register")
    public R register(@Validated({Register.class}) UserEntity user, MultipartFile uploadFile,Integer verifyCode,HttpServletResponse response, HttpSession session){

        log.info("进入register，用户为{}",user);
        return userService.register(user,uploadFile,verifyCode,response,session);
    }

    @GetMapping("/code/{email}")
    public R code(@PathVariable("email") @Email(message = "邮箱格式不正确") String email){

        CompletableFuture.runAsync(()->{
            userService.sendEmail(email);
        },executor);

        return new R(RespBeanEnum.SUCCESS,null);
    }

    /**
     * 登录
     * @param user
     * @param session
     * @param response
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody @Validated({Login.class}) UserEntity user, HttpSession session, HttpServletResponse response){

        log.info("当前登录用户{}",user);
        return userService.login(user,session,response);
    }

    @GetMapping("/showUser")
    public R showUser(){

        UserEntity user = HandlerInterceptor.loginUser.get();
        UserEntity userEntity = this.userService.showUser(user.getId());

        return new R(RespBeanEnum.SUCCESS,userEntity);
    }

    /**
     * 修改密码
     * @param oldPass 旧密码
     * @param newPass 新密码
     * @return
     */
    @GetMapping("/updatePassword/{oldPass}/{newPass}")
    public R updatePassword(@PathVariable("oldPass")String oldPass,@PathVariable("newPass")String newPass){
       return this.userService.updatePassword(newPass,oldPass);
    }

    /**
     * 信息
     */
    @Autowired
    QaFeign qaFeign;
    @RequestMapping("/info")
    public R info(){

        List<Long> longs = new ArrayList<>();
        longs.add(1l);
        longs.add(2l);
        longs.add(3l);
        this.qaFeign.searchProblems(longs);
        return  null;
    }

    @GetMapping("/catAdvice")
    public R catAdvice(){
        UserEntity user = HandlerInterceptor.loginUser.get();
        boolean advice = this.userService.isAdvice(user.getId());
        return new R(RespBeanEnum.SUCCESS,advice);
    }

    @GetMapping("/cleanAdvice")
    public R cleanAdvice(){
        UserEntity user = HandlerInterceptor.loginUser.get();
        boolean b = this.userService.cleanAdvice(user.getId());
        return new R(RespBeanEnum.SUCCESS,b);
    }


}
