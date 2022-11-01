package com.weijia.zh.manage.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Value("${file.staticAccessPath}")
//    private String sa;
//
//    @Value("${file.uploadFolder}")
//    private String upfl;

    //    配置虚拟路径
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(sa).addResourceLocations("file:"+upfl);
//    }

    // 分页插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public IKeyGenerator keyGenerator() {
        return new H2KeyGenerator();
    }


    @Autowired
    private HandlerInterceptor handlerInterceptor;
    /**
     * 配置需要拦截的路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //使用被容器管理的拦截器，拦截器中才可以注入对象
        registry.addInterceptor(handlerInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login");
    }

}
