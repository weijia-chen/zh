package com.weijia.zh.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.entity.LikeEntity;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 点赞记录
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
public interface LikeService{


    /**
     * 用户点赞回答
     * @param replyId
     * @return
     */
    R likeReply(Long replyId);

    Long sumLikeByUserId(Long userId);
}

