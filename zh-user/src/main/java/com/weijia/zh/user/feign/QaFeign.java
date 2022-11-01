package com.weijia.zh.user.feign;

import com.weijia.zh.common.entity.ProblemEntity;
import com.weijia.zh.common.entity.TopicEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "zh-qa")
@Service
public interface QaFeign {

    @GetMapping("/like/sumLikeByUserId")
    Long sumLikeByUserId(@RequestParam("userId") Long userId);

    @GetMapping("/topic/searchTopics02")
    List<TopicEntity> searchTopics02();

    @PostMapping("/problem/searchProblems")
   List<ProblemEntity> searchProblems(@RequestBody List<Long> ids);

    @GetMapping("/problem/dcrProblemFollow")
    Boolean dcrProblemFollow(@RequestParam("problemId")Long problemId);

    @GetMapping("/problem/checkProblemIsFail")
    Boolean checkProblemIsFail(@RequestParam("problemId")Long problemId);
}
