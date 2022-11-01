package com.weijia.zh.user.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Configuration
public class FeignConfig {


//    远程调用拦截器
    @Bean
    public RequestInterceptor requestInterceptor(){

        return  new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
//                使用RequestContextHolder拿到当前线程请求（从前端带过来的）的信息；底层还是使用ThreadLocal；
//                注意：如果是异步远程调用，当前线程不是分配给前端请求的线程，就需要在远程调用之前，把请求信息设置给当前线程;否则getRequestAttributes会报空指针异常
//                原因：不同线程的ThreadLocal是不能共享的
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if(attributes != null){
                    System.out.println("远程调用之前调用RequestInterceptor.apply");
                    HttpServletRequest request = attributes.getRequest();//拿到旧请求的信息
//                构建新请求的头信息。同步请求头信息，主要同步Cookie。
                    String cookie = request.getHeader("Cookie");
                    log.info("同步给远程feign的cookie：{}",cookie);
                    requestTemplate.header("Cookie",cookie);
                }
                log.info("attributes:{}",attributes);


            }
        };
    }

//    打印远程调用日志
    @Bean
    Logger.Level feignLoggerLevel()
    {
        return Logger.Level.FULL;
    }
}
