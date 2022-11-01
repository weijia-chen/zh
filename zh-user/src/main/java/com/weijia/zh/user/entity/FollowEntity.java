package com.weijia.zh.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

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
@TableName("tb_follow")
public class FollowEntity {

	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 被关注用户ID
	 */
	private Long targetId;

	/**
	 * 关注的时间
	 */
	private Date followTime;

}
