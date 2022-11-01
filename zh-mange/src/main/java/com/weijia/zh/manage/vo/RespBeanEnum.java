package com.weijia.zh.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 枚举类定义所有返回状态码和提示消息
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum implements RespBean {
    //通用
    SUCCESS(200, ""),
    ERROR(500, "服务端异常"),

    //登录模块5002xx
    LOGIN_ERROR(5002, "账号或密码不正确"),
    LOGIN_SUCCESS(200,"登录成功"),
    LOGIN_ADMINS_ERROR(4001,"还没有登录"),

    //问题审核模块
    PROBLEM_EMPTY_ERROR(6001,"暂无需要审核的问题"),
    PROBLEM_REPEAT_ERROR(6002,"该问题已经审核过啦")

    ;
    private final Integer code;
    private final String message;
}
