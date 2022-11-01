package com.weijia.zh.qa.controller;

import java.util.Arrays;
import java.util.Map;

import com.weijia.zh.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.qa.entity.CommentEntity;
import com.weijia.zh.qa.service.CommentService;

import javax.servlet.http.HttpSession;


/**
 * 
 *
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/searchComments/{replyId}")
    public R searchComments(@PathVariable("replyId")Long replyId){

        return this.commentService.searchCommentsByReplyId(replyId);
    }

    @PostMapping("/insertComment")
    public R insertComment(@RequestBody @Validated CommentEntity comment){

        return this.commentService.insertComment(comment);
    }

}
