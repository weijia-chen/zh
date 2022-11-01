package com.weijia.zh.qa.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 问题
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Data
@TableName("tb_problem")
public class ProblemEntity {


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
	 * 内容
	 */
	private String content;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 修改日期
	 */
	private Date updateTime;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 昵称
	 */
	private String userName;
	/**
	 * 浏览量
	 */
	private Long visits;
	/**
	 * 关注数
	 */
	private Long follow;
	/**
	 * 回复数
	 */
	private Long reply;

	/**
	 * 审核状态，0：未审核，1：通过，2：违规
	 */
	@TableField(value = "`condition`")
	private Integer condition;

	private String cause;
	/**
	 * 所属话题
	 */
	private Long topicId;

	/**
	 * 是否关注该问题:true为关注,其余的也一样的
	 */
	@TableField(exist = false)
	private boolean pay;

}
