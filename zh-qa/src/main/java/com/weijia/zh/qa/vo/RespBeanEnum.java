package com.weijia.zh.qa.vo;

import com.weijia.zh.common.utils.RespBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 枚举类定义所有返回状态码和提示消息
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum implements RespBean {
    //通用
    SUCCESS(200, ""),
    ERROR(500, "服务端异常"),
    LOGIN_ADMINS_ERROR(4001,"还没有登录"),

//    问题模块
    PROBLEM_ENQUIRE_SUCCESS(200,"提问成功"),
    PROBLEM_COLLECT_SUCCESS(200,"关注问题成功"),
    PROBLEM_DELETE_SUCCESS(200,"删除问题成功"),
    PROBLEM_DELETE_ERROR(5001,"问题不存在或权限不足"),
    PROBLEM_SEARCH_ERROR(5001,"问题不存在或问题违规"),
    PROBLEM_FAIL_ERROR(5002,"问题已经被判定为违规"),

//    点赞模块
    THUMP_UP_SUCCESS(200,"点赞成功"),
    THUMP_UP_ERROR(6000,"你已经赞过了"),

//    评论模块
    COMMENT_SUCCESS(200,"评论成功"),
    COMMENT_ERROR(7000,"评论失败"),

//    回答模块
    REPLY_SUCCESS(200,"发表回答成功"),
    REPLY_ERROR(8000,"发表回答失败"),
    REPLY_DELETE_SUCCESS(200,"删除回答成功"),
    REPLY_DELETE_ERROR(8001,"回答不存在或权限不足"),
    REPLY_SEARCH_ERROR(8002,"已经到底了"),
    REPLU_Drafts_SUCCESS(200,"保存为草稿成功"),
    REPLU_Drafts_ERROR(8003,"保存为草稿失败"),
    REPLU_SEARCH_ERROR(8003,"回答不存在"),

//    收藏回答模块
    REPLY_COLLECT_SUCCESS(200,"收藏回答成功"),
    REPLY_COLLECT_ERROR(9001,"收藏失败"),
    REPLY_DELETE_COLLECT_SUCCESS(200,"取消收藏成功"),
    REPLY_DELETE_COLLECT_ERROR(9002,"取消收藏失败"),

//    收藏夹模块
    FAVORITES_INSERT_SUCCESS(200,"创建收藏夹成功"),
    FAVORITES_INSERT_ERROR(9003,"创建收藏夹失败"),
    FAVORITES_DELETE_SUCCESS(200,"删除收藏夹成功"),
    FAVORITES_DELETE_ERROR(9003,"删除收藏夹失败"),

    ;
    private final Integer code;
    private final String message;
}
