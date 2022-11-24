package com.weijia.zh.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/***用于接收检索用户的类*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchUserVo {

    /**用户名**/
    private String nickName;
    /**账号状态**/
    private Integer condition;
    /**当前页数**/
    private Integer current;
    /**每页大小**/
    private Integer size;
}
