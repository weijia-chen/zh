package com.weijia.zh.qa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weijia.zh.qa.entity.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper
public interface CollectDao extends BaseMapper<Collect> {

    HashSet<Long> searchFavoritesIdByReplyIdAndUserId(@Param("userId")Long userId, @Param("replyId")Long replyId);

    long selectCountCollectReplyByUser(Long replyId);
}
