package com.weijia.zh.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("tb_message_problem")
public class MessageProblem {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Date createTime;
    private Long fromUserId;
    private String fromUserName;
    private Long problemId;
    private String problemTitle;
    private Long replyId;
}
