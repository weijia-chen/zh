package com.weijia.zh.qa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("tb_favorites")
public class Favorites {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long userId;

    /**0：未收藏，1：收藏*/
    @TableField(exist = false)
    private int flag;
}
