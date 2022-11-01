package com.weijia.zh.manage.controller;

import com.weijia.zh.manage.entity.Cause;
import com.weijia.zh.manage.service.CauseService;
import com.weijia.zh.manage.vo.R;
import com.weijia.zh.manage.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cause")
public class CauseController {

    @Autowired
    private CauseService causeService;

    @GetMapping("/searchCauses")
    public R searchCauses(){
        List<Cause> causes = this.causeService.searchCauseList();
        return new R(RespBeanEnum.SUCCESS,causes);
    }
}
