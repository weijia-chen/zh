package com.weijia.zh.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 问题
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Data
public class ProblemEntity {

	/**
	 * ID
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String title;

	/**
	 * 修改日期
	 */
	private Date updateTime;

	/**
	 * 昵称
	 */
	private String userName;


}
