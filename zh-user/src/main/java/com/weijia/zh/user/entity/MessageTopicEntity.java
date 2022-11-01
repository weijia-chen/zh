package com.weijia.zh.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 14:00:41
 */
@Data
@TableName("tb_message_topic")
public class MessageTopicEntity {

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
	 * 消息产生的时间
	 */
	private Date createTime;
	/**
	 * 消息的来源用户id
	 */
	private Integer fromUserId;
	/**
	 * 消息的来源用户昵称
	 */
	private String fromUserName;
	/**
	 * 产生消息的话题
	 */
	private Integer topicId;
	/**
	 * 产生消息的话题名称
	 */
	private String topicName;
	/**
	 * 消息的目标话题
	 */
	private Integer problemId;
	/**
	 * 问题的标题，用于显示
	 */
	private String problemTitle;

}
