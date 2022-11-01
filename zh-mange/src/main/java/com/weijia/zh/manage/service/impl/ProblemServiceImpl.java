package com.weijia.zh.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weijia.zh.manage.dao.ProblemDao;
import com.weijia.zh.manage.entity.Problem;
import com.weijia.zh.manage.fegin.SearchProblem;
import com.weijia.zh.manage.fegin.UserFeign;
import com.weijia.zh.manage.service.ProblemService;
import com.weijia.zh.manage.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private UserFeign userFeign;

    @Resource
    private SearchProblem searchProblem;

    @Override
    public boolean operateProblemCondition(Long problemId, String cause) {

        Problem problem = new Problem();
        problem.setId(problemId);
        problem.setCause(cause);
        problem.setCondition(2);//状态2代表违规
        int i = this.problemDao.updateById(problem);
        if (i <=0 )
            return false;
        //还需要把违规的回答删除，包括草稿回答，还有包含这个问题的所有消息，关注，
        this.delProblem(problemId);
        //还需要把ES中的问题删除
        this.searchProblem.del(problemId);
        return true;
    }

    @Override
    public boolean operateProblemCondition(Long problemId) {

        Problem problem = new Problem();
        problem.setId(problemId);
        problem.setCondition(1);//状态1代表通过
        int i = this.problemDao.updateById(problem);
        if (i <=0 )
            return false;
        return true;
    }


    @Override
    public PageVo searchProblems(Integer current, Integer size) {

        //先进行转换：页数和页面大小 -> 第几条到第几条
        Integer begin = (current-1)*size;
        List<Problem> problems = this.problemDao.searchProblems(begin,size);

        //查询总条目
        QueryWrapper<Problem> wrapper = new QueryWrapper<>();
        wrapper.eq("`condition`",0);
        Long total = this.problemDao.selectCount(wrapper);
        if (problems == null || problems.size() <= 0)
            return null;
        return new PageVo(problems,total);
    }

    @Override
    public boolean delProblem(Long problemId) {

        //先删除问题下的所有回答，根据触发器，会删除回答的相关评论和收藏关系
        this.problemDao.delRepliesByProblem(problemId);

        //删除有该问题产生的所有消息，还有问题的关注关系,远程调用
        this.userFeign.delMessageByProblemId(problemId);//所有消息表
        this.userFeign.delFollowByProblem(problemId);//关注的问题表
        return true;
    }
}
