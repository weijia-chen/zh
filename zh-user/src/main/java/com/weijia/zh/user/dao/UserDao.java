package com.weijia.zh.user.dao;

import com.weijia.zh.user.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
