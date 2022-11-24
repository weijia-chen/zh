package com.weijia.zh.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.weijia.zh.user.vo.Login;
import com.weijia.zh.user.vo.Register;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;


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
@TableName("tb_user")
public class UserEntity{
	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * md5密码
	 */
	@NotBlank(message = "必须输入密码",groups = {Register.class, Login.class})
	private String password;
	/**
	 * 昵称
	 */
	@NotBlank(message = "必须输入昵称",groups={Register.class})
	private String nickName;

	/**
	 * E-Mail
	 */
	@Email(message = "邮箱格式出错",groups = {Register.class, Login.class})
	@NotBlank(message = "必须输入邮箱",groups = {Register.class, Login.class})
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
	 * 上一次修改用户信息的时间，每个星期才能修改一次
	 */
	private Date registerTime;

	/**
	 * 未读的通知数量
	 */
	private Integer advice;

	/**用户状态：0正常，1封禁*/
	@TableField(value = "`condition`")
	private Integer condition;

	/**封号原因*/
	private String cause;

}
