package com.weijia.zh.qa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 点赞记录
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Data
@TableName("tb_like")
public class LikeEntity {


	/**
	 * 用户ID
	 */
		@TableId(type = IdType.AUTO)
	private Long userId;
	/**
	 * 回答ID
	 */
	private Long replyId;

}
