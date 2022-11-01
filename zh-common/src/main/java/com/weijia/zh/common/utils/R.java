package com.weijia.zh.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 统一的返回类
 * @param <T>
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class R<T> {

    private T data;//返回数据
    private Integer code;
    private String message;

    public R(RespBean respBean,T data){
        this.code = respBean.getCode();
        this.message = respBean.getMessage();
        this.data = data;
    }
    /**修改响应码和响应信息*/
    public void setRespBean(RespBean respBean){
        this.code = respBean.getCode();
        this.message = respBean.getMessage();
    }

}
