package com.weijia.zh.manage.controller;

import com.weijia.zh.manage.entity.Manage;
import com.weijia.zh.manage.service.ManageService;
import com.weijia.zh.manage.vo.R;
import com.weijia.zh.manage.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ManageController {

    @Autowired
    private ManageService manageService;


    @PostMapping("/login")
    public R login(@RequestBody Manage manage){
        log.info("manage:{}",manage);
        boolean login = this.manageService.login(manage);
        if (login)
            return new R(RespBeanEnum.SUCCESS,null);
        return new R(RespBeanEnum.LOGIN_ERROR,null);
    }
}
