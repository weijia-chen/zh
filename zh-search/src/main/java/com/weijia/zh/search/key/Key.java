package com.weijia.zh.search.key;


import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Key {

    @Value("${index}")
    private  String index;
}
