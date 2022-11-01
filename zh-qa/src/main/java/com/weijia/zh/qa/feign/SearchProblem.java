package com.weijia.zh.qa.feign;

import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.entity.ProblemEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "zh-search")
@Service
public interface SearchProblem {
    @PostMapping("/es/add")
    R addES(@RequestBody ProblemEntity problem);

    @GetMapping("/es/search")
    List search(@RequestParam("query") String query);

    @GetMapping("/es/del")
    boolean del(@RequestParam("problemId") Long problemId);

}
