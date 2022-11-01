package com.weijia.zh.user.service;

public interface MessageManageService {

    //删除所有由该问题产生的消息
    boolean delMessageByProblemId(Long problemId);

    //删除所有由该回答产生的问题
    boolean delMessageByReplyId(Long replyId);
}
