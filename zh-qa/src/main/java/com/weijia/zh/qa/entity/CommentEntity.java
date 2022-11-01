package com.weijia.zh.qa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Data
@TableName("tb_comment")
public class CommentEntity  {


	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 评论的的用户
	 */
	private Long userId;
	/**
	 * 评论用户的昵称
	 */
	private String userName;
	/**
	 * 评论用户的头像
	 */
	private String userAvatar;

	/**
	 * 回答
	 */
	@NotNull(message = "必须输入回答id")
	private Long replyId;
	/**
	 * 评论的内容
	 */
	@NotBlank(message = "必须写入评论内容")
	private String content;
	/**
	 * 评论的时间
	 */
	private Date createTime;

	/**
	 * 评论的情绪:0是积极的，1是消极的
	 */
	private Integer mood;
}
