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
@TableName("tb_follow_problem")
public class FollowProblem {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long problemId;
    private Date followTime;
}
