package com.weijia.zh.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weijia.zh.user.entity.FollowTopic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowTopicDao extends BaseMapper<FollowTopic> {

    List<Long> searchUserFollowTopics(Long userId);
}
