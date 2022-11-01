package com.weijia.zh.qa.config;

import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.qa.dao.ProblemDao;
import com.weijia.zh.qa.entity.ProblemEntity;
import com.weijia.zh.qa.entity.ReplyEntity;
import com.weijia.zh.qa.key.Key;
import com.weijia.zh.qa.service.ProblemService;
import com.weijia.zh.qa.service.ReplyService;
import com.weijia.zh.qa.utils.RedisUtil;
import com.weijia.zh.qa.vo.HotProblem;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@EnableAsync
@Slf4j
public class FixedTime {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private Key key;

    @Autowired
    private RedissonClient redissonClient;

    @Async
    @Scheduled(cron = "30 * * * * ?")//每分钟执行一次
    public void updateHot(){

        RLock lock = this.redissonClient.getLock(this.key.getFixed());
        boolean tryLock = lock.tryLock();
        if(!tryLock)//没有获取到锁就不做了，因为这说明有其他服务器做了
            return;

        try {
            log.info("进入时任务生成热榜数据和热点数据缓存===========");
            Set<ZSetOperations.TypedTuple<Object>> problemWithSores = this.redisUtil.zReverseRangeWithScores(this.key.getVisits(), 0, -1);

            log.info("查询得到problemWithSores：{}",problemWithSores);

            this.redisUtil.del(this.key.getVisits());//删除
            log.info("删除redis的浏览量记录键");

//        将浏览量更新数据库
            boolean b = this.problemService.updateProblemVisit(problemWithSores);
            log.info("更新数据库:{}",b);

//        写入缓存,更新热点数据
            this.updateHot(problemWithSores);
            log.info("写入缓存完成");
        } finally {
            lock.unlock();
        }
    }

    /**
     * 热榜更新：
     *
     * @param problemWithSores
     */
    private void updateHot(Set<ZSetOperations.TypedTuple<Object>> problemWithSores){

        List collect = problemWithSores.stream().map(ZSetOperations.TypedTuple::getValue).collect(Collectors.toList());
        if (collect.size() < 20)
            this.fillProblems(collect);
        else
            this.drawProblems(collect);

    }

    /**
     * 当前问题超过20个，需要选取前20个
     * @param collect
     */
    private void drawProblems(List collect) {

        //先通过指定公式进行排序，然后选取前20个
        List<ProblemEntity> difference = this.problemService.searchProblemsByList(collect);
        difference = difference.stream().sorted((a,b)->{
            return ( b.getVisits() + b.getFollow() * 3 ) > (a.getVisits() + a.getFollow() * 3 )?1:-1;
        }).limit(20).collect(Collectors.toList());

        //2.创建热门问题，已经排好序的了
        List<HotProblem> hots = new ArrayList<>(difference.size());
        for (ProblemEntity problem : difference) {
            hots.add(this.ProblemToHot(problem));
        }

        //3.先删后上缓存
        this.redisUtil.del(this.key.getHot());
        this.redisUtil.lSetFromList(this.key.getHot(),hots);
        for (ProblemEntity problem : difference) {
            this.redisUtil.set(this.key.getProblem() + ":" + problem.getId(),problem,60*60*1);
        }

    }

    /**
     * 当前问题不足20个时，需要补充
     * @param
     */
    private void fillProblems(List list){

        //1.查询20条问题
        List<ProblemEntity> difference = this.problemService.difference(list, 20 - list.size());
        if (list.size() >0 )
            difference.addAll(this.problemService.searchProblemsByList(list));

        //2.创建热门问题，并进行排序
        List<HotProblem> hots = new ArrayList<>(difference.size());
        for (ProblemEntity problem : difference) {
            hots.add(this.ProblemToHot(problem));
        }
       Collections.sort(hots,(a,b)->{
           return b.getVisits() > a.getVisits()?1:-1;
       });
        //3.先删后上缓存
        this.redisUtil.del(this.key.getHot());
        this.redisUtil.lSetFromList(this.key.getHot(),hots);
        for (ProblemEntity problem : difference) {
            this.redisUtil.set(this.key.getProblem() + ":" + problem.getId(),problem,60*60*1);
        }
    }

    private HotProblem ProblemToHot(ProblemEntity problem){
        HotProblem hotProblem = new HotProblem();
        hotProblem.setId(problem.getId());
        hotProblem.setVisits(problem.getVisits() + problem.getFollow() * 3);
        hotProblem.setTitle(problem.getTitle());
        return hotProblem;
    }



}
