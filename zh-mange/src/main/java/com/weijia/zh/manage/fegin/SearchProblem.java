package com.weijia.zh.manage.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "zh-search")
@Service
public interface SearchProblem {

    @GetMapping("/es/del")
    boolean del(@RequestParam("problemId") Long problemId);

}
