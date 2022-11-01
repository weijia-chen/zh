package com.weijia.zh.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.entity.CommentEntity;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
public interface CommentService{

    /**
     * 根据回答id查询所有评论
     * @param replyId
     * @return
     */
    R searchCommentsByReplyId(Long replyId);


    /**
     * 添加一条评论
     * @return ：整条评论信息（给前端回想）
     */
    R insertComment(CommentEntity comment);
}

