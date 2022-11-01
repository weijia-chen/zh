//package com.weijia.zh.gateway.gatewat.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvaConfig implements WebMvcConfigurer {
//
//
//    /**
//     * 配置需要拦截的路径
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        //使用被容器管理的拦截器，拦截器中才可以注入对象
//        registry.addInterceptor(handlerInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/","/login");
//    }
//
//    /**
//     * 使用Bean注解将拦截器被容器管理
//     * @return
//     */
//    @Bean
//    public HandlerInterceptor handlerInterceptor(){
//        return new HandlerInterceptor();
//    }
//}
