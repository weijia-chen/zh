package com.weijia.zh.manage.service;

import com.weijia.zh.manage.vo.PageVo;

public interface ProblemService {

    /**
     * 审核操作
     * @param problemId 问题id
     * @param cause 违规原因
     * @return
     */
    boolean operateProblemCondition(Long problemId, String cause);

    /**重载，通过审核*/
    boolean operateProblemCondition(Long problemId);

    /**分页查询未审核的问题*/
    PageVo searchProblems(Integer current, Integer size);

    /**在所有表中删除所有与该问题相关信息，除问题表本身外*/
    boolean delProblem(Long problemId);

}
