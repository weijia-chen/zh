package com.weijia.zh.qa.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 回答
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Data
@TableName("tb_reply")
public class ReplyEntity {


	/**
	 * 编号
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 问题ID
	 */
	private Long problemId;
	/**
	 * 回答内容
	 */
	private String content;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 更新日期
	 */
	private Date updateTime;
	/**
	 * 回答人ID
	 */
	private Long userId;
	/**
	 * 回答人昵称
	 */
	private String userName;
	/**
	 * 回答人头像
	 */
	private String userAvatar;
	/**
	 * 点赞数
	 */
	private Long thumbUp;
	/**
	 * 逻辑删除: 0：没删除，1：删除
	 */
//	@TableLogic
//	private Integer deleted;
	/**
	 * 审核状态，0：未审核，1：通过，2：违规
	 */
	@TableField(value = "`condition`")
	private Integer condition;

	/**
	 * 0:草稿;1:已发布
	 */
	private Integer issue;

	/**
	 * 收藏数量
	 */
	private Long collect;

	/**
	 * 本回答的答主是否被当前用户关注了
	 */
	@TableField(exist = false)//不是数据库字段
	private Boolean pay;

	/**
	 * 本回答是否被当前用户点赞了
	 */
	@TableField(exist = false)//不是数据库字段
	private Boolean isPraise;

	/**
	 * 本回答是否被当前用户收藏过
	 */
	@TableField(exist = false)//不是数据库字段
	private Boolean isCollect;
}
