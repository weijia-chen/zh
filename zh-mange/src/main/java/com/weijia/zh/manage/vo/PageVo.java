package com.weijia.zh.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//分页返回的
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PageVo<T> {

    private T data;
    private Long total;
}
