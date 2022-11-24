package com.weijia.zh.user.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weijia.zh.user.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weijia.zh.user.vo.SearchUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

//    关注数
    int incrFollowCount(@Param("userId") Long userId,@Param("number")Integer number);

//    粉丝数
    int incrFansCount(@Param("userId") Long userId,@Param("number")Integer number);

    //修改通知标记
    int updateAdvice(@Param("userId")Long userId, @Param("number")Integer number);

    //查看通知标记
    int searchAdvice(@Param("userId")Long userId);

    List<UserEntity> searchUsers(@Param("current") Integer current, @Param("size")Integer size);

    IPage<UserEntity> searchUsersByNameOrCondition(Page<UserEntity> user,@Param("searchUserVo") SearchUserVo searchUserVo);
}
