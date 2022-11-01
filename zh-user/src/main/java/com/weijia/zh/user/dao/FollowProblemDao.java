package com.weijia.zh.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weijia.zh.user.entity.FollowProblem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowProblemDao extends BaseMapper<FollowProblem> {
    List<Long> searchProblemIds(Long userId);
}
