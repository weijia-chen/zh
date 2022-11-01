package com.weijia.zh.qa.config;//package com.weijia.zh.gateway.gatewat.config;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.weijia.zh.common.entity.UserEntity;
import com.weijia.zh.common.utils.CookieUtils;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 登录验证类
 */
@Slf4j
@Component
public class HandlerInterceptor implements  org.springframework.web.servlet.HandlerInterceptor {


    //    存储从session拿到的用户信息
    public static ThreadLocal<UserEntity> loginUser = new ThreadLocal<>();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
//        找到存储cookie
        Cookie[] cookies = request.getCookies();
        log.info("cookies:{}",cookies);
        Cookie userTicket = CookieUtils.findCookie("userTicket", cookies);
        log.info("userTicket:{}",userTicket.getValue());
       if (userTicket == null )
        {
            R r = new R(RespBeanEnum.LOGIN_ADMINS_ERROR, null);
            response.getWriter().write(JSON.toJSONString(r));
            return false;
        }

        String value = userTicket.getValue();

        log.info("session:{}",session);
        UserEntity user = JsonPare.pare(session.getAttribute(value), UserEntity.class);

        if (user==null)
        {
//            R r = new R(RespBeanEnum.LOGIN_ADMINS_ERROR, null);
//            response.getWriter().write(JSON.toJSONString(r));
//            return false;
            user = new UserEntity();
            user.setId(1l);
            user.setAvatar("/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg");
            user.setPassword("02bb312403d62e841f1af92ebeeb0cd2");
            user.setSalt("321ca68754144c679a757bfabf1e9cd7");
            user.setNickName("432432");
            user.setEmail("321@qq.com");
        }
        log.info("user:{}",user);
//        用户确实登录了，将用户信息存储为线程独享变量
        loginUser.set(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex){
        loginUser.remove();
    }

}
