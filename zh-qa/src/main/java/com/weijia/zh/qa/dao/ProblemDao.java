package com.weijia.zh.qa.dao;

import com.weijia.zh.qa.entity.ProblemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weijia.zh.qa.vo.ProblemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 问题
 * 
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Mapper
public interface ProblemDao extends BaseMapper<ProblemEntity> {

//    更新指定问题的回答数量
    int incrReplyNumber(Long problemId);

    int incrCollectNumber(@Param("problemId")Long problemId,@Param("number")Integer number);

    int updateProblemVisit(@Param("problemId") Object problemId,@Param("score")Object score);

    List<ProblemEntity> searchProblems(List<Long> list);

    List<ProblemVo> searchProblemsByTopicId(@Param("topicId")Long topicId,@Param("current") Long current);

    long searchProblemsCountByTopicId(Long topicId);

    List<ProblemEntity> differenceProblems(@Param("list")List list, @Param("number")Integer number);

    int checkProblemIsFail(Long problem);
}
