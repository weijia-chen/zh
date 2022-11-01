package com.weijia.zh.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.entity.ReplyEntity;
import com.weijia.zh.qa.exception.DatabaseException;
import com.weijia.zh.qa.vo.ReplyVo;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 回答
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
public interface ReplyService{

//    查询问题下的回答
    ReplyEntity searchReply(Long problemId,Long current);

    List<ReplyEntity> searchReplyById(Long id,Long userId);
    List<ReplyEntity> searchReplyById(Long id);
    List<ReplyEntity> searchHotRepliesById(List<ReplyEntity> replyEntities);
    /**
     * 更新回答点赞数量
     * @param replyId
     * @param number
     * @return
     */
    int updateReplyLike(Long replyId,Long number);
    R insertReply(ReplyEntity reply) throws DatabaseException;
    /**
     * 查询指定用户发布的所有回答
     * @param userId
     * @return
     */
    List<ReplyVo> searchReplyVo(Long userId);


    boolean delReply(Long replyId);

    /**返回用户的所有草稿回答列表*/
    List<ReplyVo> searchDrafts();

    /**更新草稿回答**/
    boolean updateDrafts(ReplyEntity reply);

    /**查询指定回答，草稿回答也可以查到*/
    ReplyEntity searchReplyByReplyId(Long replyId);
}

