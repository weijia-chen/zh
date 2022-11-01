package com.weijia.zh.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableFeignClients //fegin
@EnableDiscoveryClient //nacos
@SpringBootApplication
@EnableRedisHttpSession //整合redis作为session
public class ZhMangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhMangeApplication.class, args);
    }

}
