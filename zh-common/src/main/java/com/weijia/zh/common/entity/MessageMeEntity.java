package com.weijia.zh.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

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
public class MessageMeEntity {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 接收消息的用户
	 */
	private Long userId;
	/**
	 * 消息类型--1：回答被点赞；2:回答被评论 ;3：自己被关注；4：自己的问题有人回答；5:自己的问题被收藏了
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
	private Long problemId;
	/**
	 * 话题的标题，用于显示
	 */
	private String problemTitle;
	/**
	 * 消息的目标回答
	 */
	private Integer replyId;

}
