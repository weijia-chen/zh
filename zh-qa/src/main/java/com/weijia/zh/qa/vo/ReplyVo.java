package com.weijia.zh.qa.vo;

import lombok.Data;

@Data
public class ReplyVo {

    /**回答id*/
    private Long id;
    /**
     * 回答内容
     */
    private String content;
    /**问题id*/
    private Long problemId;
    /**
     * 问题标题
     */
    private String title;

}
