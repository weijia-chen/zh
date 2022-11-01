package com.weijia.zh.manage.fegin;

import com.weijia.zh.manage.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "zh-user")
@Service
public interface UserFeign {

    @GetMapping("/messageManage/delMessageByProblemId")
    R delMessageByProblemId(@RequestParam("problemId")Long problemId);

    @GetMapping("/messageManage/delMessageByReplyId")
    R delMessageByReplyId(@RequestParam("replyId")Long replyId);

    @GetMapping("/follow/delFollowByProblem")
    Boolean delFollowByProblem(@RequestParam("problemId")Long problemId);
}
