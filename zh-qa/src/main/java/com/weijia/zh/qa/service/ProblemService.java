package com.weijia.zh.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weijia.zh.common.entity.CollectEntity;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.entity.ProblemEntity;
import org.springframework.data.redis.core.ZSetOperations;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * 问题
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
public interface ProblemService{

    R insertProblem(ProblemEntity problem);

    /**
     * @param topicId : 指定话题的id
     * @param current ：当前的页数
     * @return
     */
    R searchProblemByTopic(Long topicId,Long current);

    ProblemEntity searchProblemById(Long problemId);
    /**
     * 增加问题的回答数量1
     * @param problemId
     * @return
     */
    int incrProblemReply(Long problemId);

    R search(String query);

    /**关注（原收藏）问题*/
    R followProblem(Long problemId);

    /**
     * 更新问题的浏览量，使用事务一次提交，减少时间
     * @param set
     * @return
     */
    boolean updateProblemVisit(Set<ZSetOperations.TypedTuple<Object>> set);

    /**
     * 获取热榜
     * @return
     */
    R searchHot();

    /**
     * 通过问题的id列表查询问题,属性少版本
     * @param ids
     * @return
     */
    List<ProblemEntity> searchProblems(List<Long> ids);

    /**
     * 完整属性
     * @param ids
     * @return
     */
    List<ProblemEntity> searchProblemsByList(List<Long> ids);
    /**
     * 查询用户发布的所有问题
     * @param userId
     * @return
     */
    List<ProblemEntity> searchProblems(Long userId);

    /**
     * 删除问题
     * @param problemId
     * @return
     */
    boolean delProblem(Long problemId);

    List<ProblemEntity> searchFollowProblems();

    /**
     * 查询number条问题，按时间降序。但不包含list中的问题
     * @param list
     * @param number
     * @return
     */
    List<ProblemEntity> difference(List list,Integer number);

    /**判断问题是否违规*/
    boolean checkProblemIsFail(Long problemId);
}

