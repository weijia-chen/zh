package com.weijia.zh.qa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weijia.zh.qa.entity.Favorites;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavoritesDao extends BaseMapper<Favorites> {
}
