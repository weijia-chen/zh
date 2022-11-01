package com.weijia.zh.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weijia.zh.manage.entity.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProblemDao extends BaseMapper<Problem> {

    /**
     * 分页查询未审核的问题
     * @param begin 当前页数
     * @param size 每页几个
     * @return
     */
    List<Problem> searchProblems(@Param("begin")Integer begin,@Param("size")Integer size);

    int delRepliesByProblem(Long problemId);
}
