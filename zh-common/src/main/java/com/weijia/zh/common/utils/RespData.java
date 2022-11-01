package com.weijia.zh.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应的data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespData<T> {

    T data;
    Long total;
}
