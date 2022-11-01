package com.weijia.zh.user.config;//package com.weijia.zh.gateway.gatewat.config;


import com.weijia.zh.common.utils.CookieUtils;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.JsonUtil;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.entity.UserEntity;
import com.weijia.zh.user.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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


//        找到存储cookie
        Cookie[] cookies = request.getCookies();
        Cookie userTicket = CookieUtils.findCookie("userTicket", cookies);
        if (userTicket == null )
        {
            //再次判断是不是管理员账号
            Cookie manageCookie = CookieUtils.findCookie("manage",cookies);
            if (cookies != null)
                return this.ManageVerify(request,response,manageCookie);

            R r = new R(RespBeanEnum.LOGIN_ADMINS_ERROR, null);
            response.getWriter().write(JsonUtil.object2JsonStr(r));
            return false;
        }
        HttpSession session = request.getSession();
        String value = userTicket.getValue();

        log.info("session:{}",session);
        UserEntity user = JsonPare.pare(session.getAttribute(value), UserEntity.class);
        log.info("user:{}",user);

        if (user==null)
        {
            R r = new R(RespBeanEnum.LOGIN_ADMINS_ERROR, null);
            response.getWriter().write(JsonUtil.object2JsonStr(r));
            return false;
//            user = new UserEntity();
//            user.setId(1l);
//            user.setAvatar("/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg");
//            user.setPassword("02bb312403d62e841f1af92ebeeb0cd2");
//            user.setSalt("321ca68754144c679a757bfabf1e9cd7");
//            user.setNickName("432432");
//            user.setEmail("321@qq.com");
        }
//        用户确实登录了，将用户信息存储为线程独享变量
        loginUser.set(user);

        return true;
    }

    public boolean ManageVerify(HttpServletRequest request, HttpServletResponse response,Cookie manageCookie) throws IOException {
        log.info("管理员访问......");
        HttpSession session = request.getSession();
        Object manage = session.getAttribute(manageCookie.getValue());
        if (manage == null){
            R r = new R(RespBeanEnum.LOGIN_ADMINS_ERROR, null);
            response.getWriter().write(JsonUtil.object2JsonStr(r));
            return false;
        }
        return true;
    }

}