package com.weijia.zh.search.controller;

import com.weijia.zh.common.entity.ProblemEntity;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.search.service.ProblemES_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
@Slf4j
@RequestMapping("/es")
@RestController
public class ProblemES_Controller {


    @Autowired
    private ProblemES_Service problemES_service;


    @PostMapping("/add")
    public R addES(@RequestBody ProblemEntity problem){

        return  this.problemES_service.addES(problem);
    }

    @GetMapping("/search")
    public List search(String query){

        log.info("进入 search 方法，参数为：{}",query);
        try {
            return this.problemES_service.searchProblemOrUserName(query);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/del")
    public boolean del(@RequestParam("problemId") Long problemId){
        log.info("del:{}",problemId);
        try {
            return this.problemES_service.delES(problemId);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
