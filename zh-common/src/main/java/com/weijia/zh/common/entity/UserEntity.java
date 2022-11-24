package com.weijia.zh.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {

	/**
	 * ID
	 */
	private Long id;
	/**
	 * md5密码
	 */

	private String password;
	/**
	 * 昵称
	 */

	private String nickName;

	/**
	 * E-Mail
	 */
	private String email;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 粉丝数
	 */
	private Integer fansCount;
	/**
	 * 关注数
	 */
	private Integer followCount;
	/**
	 * 密码后的盐
	 */
	private String salt;
	/**
	 * 收获的赞
	 */
	private Long thumbUp;
	/**
	 * 注册时间
	 */
	private Date registerTime;

}
