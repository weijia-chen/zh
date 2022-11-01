package com.weijia.zh.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowUserInfoVo {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 被关注用户ID
     */
    private Long targetId;

    private String nickName;

}
