package com.weijia.zh.manage.config;//package com.weijia.zh.gateway.gatewat.config;

import com.weijia.zh.manage.utils.CookieUtils;
import com.weijia.zh.manage.utils.JsonUtil;
import com.weijia.zh.manage.vo.R;
import com.weijia.zh.manage.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录验证类
 */
@Slf4j
@Component
public class HandlerInterceptor implements  org.springframework.web.servlet.HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (true)
            return true;

        HttpSession session = request.getSession();
//        找到存储cookie
        Cookie[] cookies = request.getCookies();
        log.info("cookies:{}",cookies);
        Cookie manage = CookieUtils.findCookie("manage", cookies);
        log.info("manage:{}",manage);
        if (manage == null )
        {
            R r = new R(RespBeanEnum.LOGIN_ADMINS_ERROR, null);
            response.getWriter().write(JsonUtil.object2JsonStr(r));
            return false;
        }
        String value = manage.getValue();
        Object manage_login = session.getAttribute(value);
        log.info("manage_login:{}",manage_login);
        if (manage_login == null) {
            R r = new R(RespBeanEnum.LOGIN_ADMINS_ERROR, null);
            response.getWriter().write(JsonUtil.object2JsonStr(r));
            return false;
        }
        return true;
    }

}