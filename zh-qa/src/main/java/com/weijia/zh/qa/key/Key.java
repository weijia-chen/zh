package com.weijia.zh.qa.key;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
public class Key {

    @Value("${redisKey.visits}")
    private  String visits;

    @Value("${redisKey.topics}")
    private String topics;


    @Value("${redisKey.problem}")
    private String problem;

    @Value("${redisKey.replies}")
    private String replies;

    @Value("${redisKey.praise}")
    private String praise;

    @Value("${redisKey.praiseUser}")
    private String praiseUser;

    @Value("${redisKey.distributed.dtopics}")
    private String dtopics;

    @Value("${redisKey.hot}")
    private String hot;

    @Value("${redisKey.fixed}")
    private String fixed;

    @Value("${pageSize}")
    private Integer pageSize;

    @Value("${hotNumber}")
    private Integer hotNumber;

    @Value("${praiseSize}")
    private Integer praiseSize;

}
