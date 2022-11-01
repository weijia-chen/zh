package com.weijia.zh.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Data
@TableName("tb_topic")
public class Topic {

	/**
	 * 话题iD
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 话题名称
	 */
	private String topicName;

	private String introduce;

}
