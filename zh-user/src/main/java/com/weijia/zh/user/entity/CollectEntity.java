package com.weijia.zh.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 收藏问题
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("tb_collect")
public class CollectEntity  {


	/**
	 * 用户id
	 */

	private Long userId;
	/**
	 * 问题id
	 */
	private Long problemId;

}
