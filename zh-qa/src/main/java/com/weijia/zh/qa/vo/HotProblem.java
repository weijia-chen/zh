package com.weijia.zh.qa.vo;

import lombok.Data;

/**
 * 热板问题 简要，用户存储到redis和展示页面的
 */
@Data
public class HotProblem {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览量
     */
    private Long visits;
}
