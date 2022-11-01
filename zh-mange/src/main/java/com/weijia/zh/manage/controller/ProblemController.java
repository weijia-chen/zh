package com.weijia.zh.manage.controller;


import com.weijia.zh.manage.service.ProblemService;
import com.weijia.zh.manage.vo.PageVo;
import com.weijia.zh.manage.vo.R;
import com.weijia.zh.manage.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    //分页查询未审核的问题
    @GetMapping("/searchProblems/{current}/{size}")
    public R searchProblems(@PathVariable("current")Integer current,@PathVariable("size")Integer size){

        PageVo pageVo = this.problemService.searchProblems(current, size);
        if (pageVo ==  null)
            return new R(RespBeanEnum.PROBLEM_EMPTY_ERROR,null);
        return new R(RespBeanEnum.SUCCESS,pageVo);
    }

    //点击审核
    @GetMapping("/operateProblemCondition")
    public R operateProblemCondition(@RequestParam("problemId")Long problemId,@RequestParam(required = false , name = "cause")String cause){
        log.info("参数：{},{}",problemId,cause);
        boolean check = false;
        if (StringUtils.isEmpty(cause))
           check = this.problemService.operateProblemCondition(problemId);
        else
           check = this.problemService.operateProblemCondition(problemId,cause);
        if (check)
            return new R(RespBeanEnum.SUCCESS,null);

        return new R(RespBeanEnum.PROBLEM_REPEAT_ERROR,null);
    }

}
