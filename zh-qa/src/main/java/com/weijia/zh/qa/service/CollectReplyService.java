package com.weijia.zh.qa.service;

import com.weijia.zh.qa.entity.Favorites;
import com.weijia.zh.qa.vo.ReplyVo;

import java.util.List;

public interface CollectReplyService {

    /**
     * 收藏回答
     * @param replyId 回答id
     * @param fid 收藏夹id
     * @return
     */
    boolean collectReply(Long replyId,Long fid);

    /**
     * 查询回答的收藏次数：按用户统计
     * @param replyId
     * @return
     */
    long countCollectReply(Long replyId);

    /**
     * 返回当前用户的所有收藏夹
     */
    List<Favorites> searchFavorites();
    /**
     * 返回当前用户的所有收藏夹，如果有收藏夹已经收藏过该回答，那么该收藏夹要被标记
     */
    List<Favorites> searchFavorites(Long replyId);

    /**取消收藏*/
    boolean delCollect(Long replyId,Long fid);

    /**查询指定收藏夹的回答*/
    List<ReplyVo> searchCollectReplyByFavorites(Long fid);

    /**创建收藏夹*/
    boolean insertFavorites(String name);

    /**删除收藏夹*/
    boolean delFavorites(Long fid);

}
