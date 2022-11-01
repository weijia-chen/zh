package com.weijia.zh.user.dao;

import com.weijia.zh.user.entity.FollowEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weijia.zh.user.vo.FollowUserInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@Mapper
public interface FollowDao extends BaseMapper<FollowEntity> {

    List<Long> searchUserFollowUsers(Long userId);

    List<Long> searchUserIdByTarget(Long targetId);

    List<FollowUserInfoVo> searchFollow(Long userId);
}
