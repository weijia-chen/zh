package com.weijia.zh.qa.entity;

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
@TableName("tb_collect")
public class Collect {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Date collectTime;
    private Long favoritesId;
    private Long replyId;
}
