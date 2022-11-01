package com.weijia.zh.qa.controller;

import java.util.Arrays;
import java.util.Map;

import com.weijia.zh.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.qa.entity.LikeEntity;
import com.weijia.zh.qa.service.LikeService;

import javax.servlet.http.HttpSession;


/**
 * 点赞记录
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@RestController
@RequestMapping("/like")
@Slf4j
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/reply/{replyId}")
    public R likeReply(@PathVariable("replyId")Long replyId){
        log.info("进入likeReply....");
        return this.likeService.likeReply(replyId);
    }

    @GetMapping("/sumLikeByUserId")
    public Long sumLikeByUserId(Long userId){

        return this.likeService.sumLikeByUserId(userId);
    }

}
