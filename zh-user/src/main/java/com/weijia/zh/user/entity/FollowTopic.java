package com.weijia.zh.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("tb_follow_topic")
public class FollowTopic {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 被关注话题ID
     */
    private Long topicId;

    @TableField(exist = false)//不是数据库字段
    private String topicName;
}
