package com.weijia.zh.user;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession //整合redis作为session
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoDataSourceProxy
@EnableRabbit
@EnableFeignClients
public class ZhUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhUserApplication.class, args);
    }

}
