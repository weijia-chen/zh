package com.weijia.zh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weijia.zh.common.entity.ProblemEntity;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.entity.CollectEntity;

import java.util.List;
import java.util.Map;

/**
 * 收藏问题
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-17 20:10:59
 */
public interface CollectService {

    R insert(Long problemId);

    R insertFollowProblem(Long problemId);

    List<ProblemEntity> searchProblems(Long userId);
}

