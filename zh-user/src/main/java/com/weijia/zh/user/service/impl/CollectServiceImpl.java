package com.weijia.zh.user.service.impl;

import com.weijia.zh.common.entity.ProblemEntity;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.user.config.HandlerInterceptor;
import com.weijia.zh.user.dao.FollowProblemDao;
import com.weijia.zh.user.entity.FollowProblem;
import com.weijia.zh.user.entity.UserEntity;
import com.weijia.zh.user.feign.QaFeign;
import com.weijia.zh.user.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.weijia.zh.user.dao.CollectDao;
import com.weijia.zh.user.entity.CollectEntity;
import com.weijia.zh.user.service.CollectService;

import java.util.Date;
import java.util.List;

@Slf4j
@Service("collectService")
public class CollectServiceImpl implements CollectService {


    @Autowired
    private CollectDao collectDao;

    @Autowired
    private FollowProblemDao followProblemDao;

    @Autowired
    private QaFeign qaFeign;

    @Override
    public R insert(Long problemId) {

        int insert =0;
        UserEntity user = HandlerInterceptor.loginUser.get();
        CollectEntity collect = new CollectEntity();
        collect.setProblemId(problemId);
        collect.setUserId(user.getId());
        try {
            insert = this.collectDao.insert(collect);
        } catch (Exception e) {
            log.info("插入有错误，应该返回异常");
            e.printStackTrace();
            return new R(RespBeanEnum.ERROR,null);
        }
        if(insert <=0)
            return new R(RespBeanEnum.ERROR,null);

        return new R(RespBeanEnum.SUCCESS,null);
    }

    @Override
    public R insertFollowProblem(Long problemId) {
        int insert =0;
        UserEntity user = HandlerInterceptor.loginUser.get();
        FollowProblem followProblem = new FollowProblem();
        followProblem.setProblemId(problemId);
        followProblem.setUserId(user.getId());
        try {
            insert =  this.followProblemDao.insert(followProblem);
        } catch (Exception e) {
            log.info("插入有错误，应该返回异常");
            e.printStackTrace();
            return new R(RespBeanEnum.ERROR,null);
        }
        if(insert <=0)
            return new R(RespBeanEnum.ERROR,null);

        return new R(RespBeanEnum.SUCCESS,null);
    }

    @Override
    public List<ProblemEntity> searchProblems(Long userId) {

//        先查所有收藏的问题id
        List<Long> longs = this.followProblemDao.searchProblemIds(userId);
        List<ProblemEntity> problemEntities = this.qaFeign.searchProblems(longs);
        log.info("远程调用得到的问题列表：{}",problemEntities);
        return problemEntities;


    }
}