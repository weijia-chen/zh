package com.weijia.zh.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

    private Long id;

    /**
     * 消息种类
     */
    private Integer kind;
    /**
     * 接收消息的用户
     */
    private Long userId;
    /**
     * 消息产生的时间
     */
    private Date createTime;
    /**
     * 消息的来源用户id
     */
    private Long fromUserId;
    /**
     * 消息的来源用户昵称
     */
    private String fromUserName;
    /**
     * 产生消息的话题
     */
    private Long topicId;
    /**
     * 产生消息的话题名称
     */
    private String topicName;
    /**
     * 消息的目标话题
     */
    private Long problemId;
    /**
     * 问题的标题，用于显示
     */
    private String problemTitle;
    /**
     * 回答id
     */
    private Long replyId;
}
