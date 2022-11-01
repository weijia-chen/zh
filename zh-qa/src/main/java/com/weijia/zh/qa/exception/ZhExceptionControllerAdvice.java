package com.weijia.zh.qa.exception;


import com.alibaba.nacos.common.utils.CollectionUtils;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;


/**
 * 全局的异常处理
 * @RestControllerAdvice:
 *  --是由
 *  @ControllerAdvice ：给控制器添加统一的操作或处理。
 *  和 @ResponseBody ：将方法的返回结果对象转为Json格式，不满足（k-v格式）则转成字符串
 *   组成的符合注解
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.weijia.zh.qa.controller")
public class ZhExceptionControllerAdvice {

    /**
     * @ExceptionHandler:异常拦截器，处理异常的
     */

    /**
     * ConstraintViolationException:接口（controller层）中参数校验不通过
     * MethodArgumentNotValidException:以json格式提交有效，使用@RequestBody
     * BindException: 对表单提交有效
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value ={MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class}) //参数检验不通过就会到这里来
    public R handleVaildException(Exception e){
        log.error("数据校验出现问题{}，异常类型：{}",e.getMessage(),e.getClass());


        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException me = (MethodArgumentNotValidException) e;
            log.error("来到MethodArgumentNotValidException=======");
            return wrapperBindingResult(me.getBindingResult());
        }
        if (e instanceof BindException) {
            BindException be = (BindException) e;
            log.error("来到BindException=======");
            return wrapperBindingResult(be.getBindingResult());
        }
        if (e instanceof ConstraintViolationException) {
            log.error("来到ConstraintViolationException=======");
            return wrapperConstraintViolationExceptionResult((ConstraintViolationException) e);
        }
        return new R(RespBeanEnum.ERROR,null);
    }


    @ExceptionHandler(value = RuntimeException.class)
    public R handleException(RuntimeException e){

        log.info("进入RuntimeException.class的处理");
        e.printStackTrace();
        return new R(null,5000,e.getMessage());
    }

    private R wrapperConstraintViolationExceptionResult(ConstraintViolationException e) {
            StringBuilder msg = new StringBuilder();
            Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
            if (CollectionUtils.isEmpty(constraintViolations)) {
                return new R(RespBeanEnum.ERROR,null);
            }
            for (ConstraintViolation<?> item : constraintViolations) {
                String propertyPath = item.getPropertyPath().toString();
                msg.append(", ").append(propertyPath.split("\\.")[1]).append(": ").append(item.getMessage());
            }
            return  new R(null,4000,msg.toString());
}


    private R wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (CollectionUtils.isEmpty(allErrors)) {
            return new R(RespBeanEnum.ERROR,null);
        }
        for (ObjectError error : allErrors) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }

        return new R(null,4000,msg.toString());
    }
}
