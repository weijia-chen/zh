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
public class MessageFollowEntity {


	private Long id;
	/**
	 * 接收消息的用户
	 */
	private Long userId;
	/**
	 * 消息类型--
	 1：关注的人发布回答；2:关注的人提出问题
	 */
	private Integer kind;
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
	 * 消息的目标话题
	 */
	private Integer problemId;
	/**
	 * 话题的标题，用于显示
	 */
	private String problemTitle;


}
