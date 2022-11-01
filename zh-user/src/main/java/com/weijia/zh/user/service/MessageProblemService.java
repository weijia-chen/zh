package com.weijia.zh.user.service;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.common.utils.RespData;

public interface MessageProblemService {

    /**
     * 分页查询关注问题的动态
     * @param current
     * @return
     */
    R<RespData> searchMessageProblems(Integer current);
}
