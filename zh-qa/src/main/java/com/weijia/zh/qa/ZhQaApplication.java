package com.weijia.zh.qa;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
@EnableFeignClients
@EnableRedisHttpSession //整合redis作为session
@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoDataSourceProxy
@EnableRabbit //开启rabbitmq 功能
public class ZhQaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZhQaApplication.class, args);
    }
}
