//package com.weijia.zh.gateway.gatewat.config;
//
//
//import com.alibaba.druid.support.json.JSONUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//
///**
// * 登录验证类
// */
//public class HandlerInterceptor implements  org.springframework.web.servlet.HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        HttpSession session = request.getSession();
//        Object userTicket = session.getAttribute("userTicket");
//
//
//        if (userTicket==null)
//        {
//            HashMap<Object, Object> map = new HashMap<>();
//            map.put("code","4000");
//            map.put("message","还没有登录");
//            response.getWriter().write(JSONUtils.toJSONString(map));
//            return false;
//        }
//
//        return true;
//    }
//
//}
