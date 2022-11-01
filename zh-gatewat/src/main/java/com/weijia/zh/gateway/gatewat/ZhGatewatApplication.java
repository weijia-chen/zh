package com.weijia.zh.gateway.gatewat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class ZhGatewatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhGatewatApplication.class, args);
    }

}
