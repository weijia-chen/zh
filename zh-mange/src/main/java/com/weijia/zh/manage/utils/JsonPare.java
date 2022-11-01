package com.weijia.zh.manage.utils;

import com.alibaba.fastjson.JSON;

public class JsonPare {

    public static  <T> T pare(Object object,Class<T> clazz){

        String s = JSON.toJSONString(object);
        return JSON.parseObject(s,clazz);
    }
}
