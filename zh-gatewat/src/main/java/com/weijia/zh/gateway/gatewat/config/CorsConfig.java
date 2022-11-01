package com.weijia.zh.gateway.gatewat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 解决跨域问题，并携带cookie
 */
@Configuration
public class CorsConfig {


    //可以跨域访问服务器端的地址
    @Value("${origin}")
    private String origin;

    @Bean
    public CorsWebFilter corsFilter() {
        // 添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        // 放行哪些原始域
        config.addAllowedOrigin(origin);
        config.addAllowedOrigin("http://localhost:8083");
        // 是否发送Cookie信息
        config.setAllowCredentials(true);
        // 放行哪些原始域(请求方式)
        config.addAllowedMethod("*");
        // 放行哪些原始域(头部信息)
        config.addAllowedHeader("*");


        // 添加映射路径
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        // 返回新的CorsFilter.
        return new CorsWebFilter(configSource);
    }
}
