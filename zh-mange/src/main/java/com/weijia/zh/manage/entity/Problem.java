package com.weijia.zh.manage.entity;

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
@TableName("tb_problem")
public class Problem {

    private Long id;

    private String title;

    private String content;

    /**
     * 0：未审核
     * 1：通过
     * 2：违规
     */
    @TableField(value = "`condition`")
    private Integer condition;//是否违规

    private String cause;// 违规原因

}
