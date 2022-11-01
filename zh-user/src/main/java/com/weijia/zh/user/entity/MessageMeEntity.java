package com.weijia.zh.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("tb_message_me")
public class MessageMeEntity {

	/**
	 * id
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;
	/**
	 * 接收消息的用户
	 */
	private Long userId;
	/**
	 * 消息类型--1：回答被点赞；2:回答被评论 ;3：自己被关注；4：自己的问题有人回答；5:自己问题被关注
	 */
	private Integer kind;
	/**
	 * 消息产生的时间
	 */
	private Date createTime;
	/**
	 * 消息的来源用户id
	 */
	private Long fromUserId;
	/**
	 * 消息的来源用户昵称
	 */
	private String fromUserName;
	/**
	 * 消息的目标话题
	 */
	private Integer problemId;
	/**
	 * 话题的标题，用于显示
	 */
	private String problemTitle;

	/**
	 * 消息的目标回答
	 */
	private Long replyId;

}
