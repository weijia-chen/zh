package com.weijia.zh.user.vo;

import com.weijia.zh.common.utils.RespBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

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
    LOGIN_ERROR(500210, "账号或密码不正确"),
    LOGIN_SUCCESS(200,"登录成功"),
    LOGIN_ADMINS_ERROR(4001,"还没有登录"),
    LOGIN_ERROR_EMAIL(4002,"该邮箱还没有注册"),


//    注册模块
    REGISTER_ERROR(60001,"该邮箱已经存在"),
    REGISTER_ERROR_CODE(60002,"验证码错误"),
    REGISTER_SUCCESS(200,"注册成功"),

    UPLOAD_SUCCESS(200,"上传成功"),

    UPDATE_PASSWORD_SUCCESS(200,"修改密码成功"),
    UPDATE_PASSWORD_ERROR(70001,"原密码错误"),

    DEL_FOLLOW_SUCCESS(200,"取关成功"),
    DEL_FOLLOW_ERROR(8001,"取关失败"),

    PROBLEM_FAIL_ERROR(5002,"问题已经被判定为违规"),
    ;
    private final Integer code;
    private final String message;
}
