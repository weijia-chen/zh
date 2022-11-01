package com.weijia.zh.user.dao;

import com.weijia.zh.user.entity.CollectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收藏问题
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@Mapper
public interface CollectDao extends BaseMapper<CollectEntity> {

    List<Long> searchProblemIds(Long userId);
}
