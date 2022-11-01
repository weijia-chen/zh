package com.weijia.zh.qa.feign;

import com.weijia.zh.common.entity.CollectEntity;
import com.weijia.zh.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "zh-user")
@Service
public interface UserFeign {

    @PostMapping("/collect/insert")
    R insert(@RequestBody Long problem);

    @GetMapping("/follow/searchUserFollowTopics")
    List<Long> searchUserFollowTopics(@RequestParam("userId") Long userId);

    @GetMapping("/follow/searchUserFollowUsers")
    List<Long> searchUserFollowUsers(@RequestParam("userId") Long userId);

    @GetMapping("/messageManage/delMessageByProblemId")
    R delMessageByProblemId(@RequestParam("problemId")Long problemId);

    @GetMapping("/messageManage/delMessageByReplyId")
    R delMessageByReplyId(@RequestParam("replyId")Long replyId);

    @GetMapping("/follow/isFollow")
    boolean isFollow(@RequestParam("targetId") Long targetId);

    @GetMapping("/follow/isFollowProblem")
    boolean isFollowProblem(@RequestParam("problemId") Long problemId);

    @GetMapping("/follow/searchFollowProblems")
    List<Long> searchFollowProblems();
}
