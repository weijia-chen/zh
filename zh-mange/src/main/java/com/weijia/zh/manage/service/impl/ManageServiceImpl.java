package com.weijia.zh.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijia.zh.manage.dao.ManageDao;
import com.weijia.zh.manage.entity.Manage;
import com.weijia.zh.manage.service.ManageService;
import com.weijia.zh.manage.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class ManageServiceImpl implements ManageService {

    @Autowired
    private ManageDao manageDao;

    @Autowired
    HttpSession session;

    @Autowired
    HttpServletResponse response;

    @Override
    public boolean login(Manage manage) {

        QueryWrapper<Manage> wrapper = new QueryWrapper<>();
        wrapper.eq("id",manage.getId());
        wrapper.eq("password",manage.getPassword());
        boolean exists = this.manageDao.exists(wrapper);
        if (!exists)
            return false;
        //加入session
        String uuid = UUIDUtil.uuid();
        session.setAttribute(uuid,manage);
        Cookie cookie = new Cookie("manage",uuid);
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }
}
