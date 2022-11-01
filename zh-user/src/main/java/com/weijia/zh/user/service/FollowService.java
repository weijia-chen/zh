package com.weijia.zh.user.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.entity.FollowEntity;
import com.weijia.zh.user.entity.FollowTopic;
import com.weijia.zh.user.vo.FollowUserInfoVo;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
public interface FollowService  {

    /**
     * 关注话题
     * @param topicId
     * @return
     */
    R followTopic(Long topicId);

    /**
     * 关注用户
     * @param userId
     * @return
     */
    R followUser(Long userId);


    /**
     * 指定用户关注的所有话题
     * @param userId
     * @return
     */
    List<Long> searchUserFollowTopics(Long userId);

    /**
     * 指定用户关注的所有用户
     * @return
     */
    List<Long> searchUserFollowUsers(Long userId);

    List<FollowUserInfoVo> searchFollowUsers(Long userId);

    List<FollowTopic> searchFollowTopics(Long userId);


    boolean delFollowUser(Long userId);

    boolean delFollowProblem(Long problemId);

    boolean delFollowTopic(Long topicId);

    /**
     * 是否关注 targetId
     * @param targetId
     * @return
     */
    boolean isFollow(Long targetId);

    boolean isFollowProblem(Long problemId);

    /**用户关注的所有问题*/
    List<Long> searchFollowProblems(Long userId);

    /**根据问题id，删除关注该问题的所有关系*/
    boolean delFollowByProblem(Long problemId);
}

