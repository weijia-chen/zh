package com.weijia.zh.search.service;

import com.weijia.zh.common.entity.ProblemEntity;
import com.weijia.zh.common.utils.R;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface ProblemES_Service {

    /**
     * 将问题加入全文检索
     * @param problem
     * @return
     */
    R addES(ProblemEntity problem);

    boolean delES(Long problemId) throws IOException;

    /**
     * 根据问题关键词或作者姓名进行模糊查询。取前100条分数最高的
     * @param query
     * @return
     */
    List searchProblemOrUserName(String query) throws IOException;
}
