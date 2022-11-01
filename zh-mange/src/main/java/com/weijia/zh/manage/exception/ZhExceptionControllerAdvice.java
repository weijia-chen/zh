package com.weijia.zh.manage.exception;


import com.weijia.zh.manage.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局的异常处理
 * @RestControllerAdvice:
 *  --是由
 *  @ControllerAdvice ：给控制器添加统一的操作或处理。
 *  和 @ResponseBody ：将方法的返回结果对象转为Json格式，不满足（k-v格式）则转成字符串
 *   组成的符合注解
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.weijia.zh.manage.controller")
public class ZhExceptionControllerAdvice {

    /**
     * @ExceptionHandler:异常拦截器，处理异常的
     */

    @ExceptionHandler(value = RuntimeException.class)
    public R handleException(RuntimeException e){

        log.info("进入RuntimeException.class的处理");
        e.printStackTrace();
        return new R(null,5000,e.getMessage());
    }
}
