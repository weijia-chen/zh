package com.weijia.zh.qa.controller;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.weijia.zh.common.entity.CollectEntity;
import com.weijia.zh.common.entity.UserEntity;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;

import com.weijia.zh.qa.config.HandlerInterceptor;
import com.weijia.zh.qa.dao.ProblemDao;
import com.weijia.zh.qa.feign.SearchProblem;
import com.weijia.zh.qa.feign.UserFeign;
import com.weijia.zh.qa.service.RabbitmqProduct;
import com.weijia.zh.qa.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.qa.entity.ProblemEntity;
import com.weijia.zh.qa.service.ProblemService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


/**
 * 问题
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Slf4j
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemDao problemDao;


    /**
     * 插入问题:
     */
    @PostMapping("/insert")
    public R insert(@RequestBody ProblemEntity problem){
        log.info("成功进入insert方法，传入数据为,{}",problem);
        return problemService.insertProblem(problem);
    }

    /**查询指定话题的问题，用户列表展示，返回的problemVo**/
    @GetMapping("/searchProblemByTopic")
    public  R searchProblemByTopic(@Param("topicId")Long topicId,@Param("current") Long current,@Param("pageSize")Long pageSize) {
        return this.problemService.searchProblemByTopic(topicId,current,pageSize);
    }

    /**
     * 查询问题详情
     * @param problemId 问题id
     * @return
     */
//    @SentinelResource(value = "searchProblem")
    @GetMapping("/searchProblem/{problemId}")
    public R searchProblem(@PathVariable("problemId") Long problemId) {
        ProblemEntity problem = this.problemService.searchProblemById(problemId);
        if (problem != null)
            return new R(RespBeanEnum.SUCCESS,problem);
        return new R(RespBeanEnum.PROBLEM_SEARCH_ERROR,null);
    }

    @GetMapping("/search/{query}")
    public R search(@PathVariable("query")String query){
        log.info("query:{}",query);
        return this.problemService.search(query);
    }

    /**
     * 关注问题
     * @param problemId 问题id
     * @return
     */
    @GetMapping("/followProblem/{problemId}")
    public R followProblem(@PathVariable("problemId")Long problemId){
        return this.problemService.followProblem(problemId);
    }

    /**
     * 问题关注数减1
     * @param problemId
     * @return
     */
    @GetMapping("/dcrProblemFollow")
    public Boolean dcrProblemFollow(@RequestParam("problemId")Long problemId){
        log.info("dcrProblemFollow:",problemId);
        this.problemDao.incrCollectNumber(problemId,-1);
        return true;
    }

//    searchHot
    @GetMapping("/searchHot")
    public R searchHot(){
        return this.problemService.searchHot();
    }

    @PostMapping("/searchProblems")
    public List<ProblemEntity> searchProblems(@RequestBody List<Long> ids){
        log.info("ids:{}",ids);
//        如果为空，就直接返回空
        if (ids == null || ids.size()<=0){
            return new ArrayList<>();
        }
        return this.problemService.searchProblems(ids);
    }

    @GetMapping("/searchProblemsByUserId")
    public R searchProblemsByUserId(){
        UserEntity user = HandlerInterceptor.loginUser.get();
        List<ProblemEntity> problemEntities = this.problemService.searchProblems(user.getId());
        return new R(RespBeanEnum.SUCCESS,problemEntities);
    }

    /**
     * 删除问题
     * @param problemId 问题id
     * @return
     */
    @GetMapping("/delProblem/{problemId}")
    public R delProblem(@PathVariable("problemId")Long problemId){
        log.info("delProblem,problemId:{}",problemId);
        boolean b = this.problemService.delProblem(problemId);
        if (b)
            return new R(RespBeanEnum.PROBLEM_DELETE_SUCCESS,null);
        return new R(RespBeanEnum.PROBLEM_DELETE_ERROR,null);
        }

    /**
     * 查询用户关注的所有问题
     * @return
     */
    @GetMapping("/searchFollowProblems")
    public R searchFollowProblems(){
        List<ProblemEntity> problems = this.problemService.searchFollowProblems();
        return new R(RespBeanEnum.SUCCESS,problems);
    }

    @GetMapping("/checkProblemIsFail")
    public Boolean checkProblemIsFail(@RequestParam("problemId")Long problemId){
        return this.problemService.checkProblemIsFail(problemId);
    }
}
