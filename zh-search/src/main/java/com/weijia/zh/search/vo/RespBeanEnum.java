package com.weijia.zh.search.vo;

import com.weijia.zh.common.utils.RespBean;
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

    ;
    private final Integer code;
    private final String message;
}
