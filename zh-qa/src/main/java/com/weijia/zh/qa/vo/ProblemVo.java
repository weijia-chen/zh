package com.weijia.zh.qa.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_problem")
public class ProblemVo {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 修改日期
     */
    private Date updateTime;

    /**
     * 回复数
     */
    private Long reply;
    /**
     * 逻辑删除: 0：没删除，1：删除
     */
//    @TableLogic
//    private Integer deleted;
}
