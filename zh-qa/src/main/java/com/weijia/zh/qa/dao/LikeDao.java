package com.weijia.zh.qa.dao;

import com.weijia.zh.qa.entity.LikeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 点赞记录
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Mapper
public interface LikeDao extends BaseMapper<LikeEntity> {

    long sumLikeByUserId(Long userId);

}
