package com.weijia.zh.qa.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.weijia.zh.common.entity.UserEntity;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.config.HandlerInterceptor;
import com.weijia.zh.qa.entity.ProblemEntity;
import com.weijia.zh.qa.exception.DatabaseException;
import com.weijia.zh.qa.vo.ReplyVo;
import com.weijia.zh.qa.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weijia.zh.qa.entity.ReplyEntity;
import com.weijia.zh.qa.service.ReplyService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;


/**
 * 回答
 * @author chenweijia
 * @email 1761807892@qq.com
 * @date 2022-04-21 22:32:52
 */
@Slf4j
@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    /**
     * 首次编辑回答
     * 保存或发布回答
     * 保存为草稿：issue 为0
     * 直接发布：issue 为1
     * @param reply
     * @return
     * @throws DatabaseException
     */
    @PostMapping("/insertReply")
    public R insertReply(@RequestBody ReplyEntity reply) throws DatabaseException {
        log.info("传入的回答内容:{}",reply.getContent());
        String s = StringEscapeUtils.unescapeHtml(reply.getContent());
        log.info("传入的回答内容:{}",s);
        reply.setContent(s);
        R r = this.replyService.insertReply(reply);
        if (reply.getIssue() == 0){
            if (r.getCode() == 200)
                r.setRespBean(RespBeanEnum.REPLU_Drafts_SUCCESS);
            else
                r.setRespBean(RespBeanEnum.REPLU_Drafts_ERROR);
        }
        return r;
    }

    /**
     * 修改草稿回答后保存或发布；修改已经发布的回答内容
     *      * 保存为草稿：issue 为0
     *      * 直接发布：issue 为1
     * 需要的请求参数只要ReplyEntity的3个参数：
     * 1）草稿回答编号id
     * 2）修改后的回答内容content
     * 3)是否发布issue
     * 如果没有任何修改，也需要把回答的内容content传过来
     * @param reply 回答对象
     * @return
     */
    @PostMapping("/updateDrafts")
    public R updateDrafts(@RequestBody ReplyEntity reply){
        log.info("传入的回答内容:{}",reply.getContent());
        String s = StringEscapeUtils.unescapeHtml(reply.getContent()); //转义，将html语句转为普通字符串
        log.info("传入的回答内容:{}",s);
        reply.setContent(s);
        boolean b = this.replyService.updateDrafts(reply);
        if (b){
            if (reply.getIssue() == 1)
                return new R(RespBeanEnum.REPLY_SUCCESS,null);
            return new R(RespBeanEnum.REPLU_Drafts_SUCCESS,null);
        }
        else {
            if (reply.getIssue() == 1)
                return new R(RespBeanEnum.REPLY_ERROR,null);
            return new R(RespBeanEnum.REPLU_Drafts_ERROR,null);
        }
    }

    @GetMapping("/searchReplyVosByUserId")
    public R searchReplyVosByUserId(){
        UserEntity user = HandlerInterceptor.loginUser.get();
        List<ReplyVo> replyVos = this.replyService.searchReplyVo(user.getId());
        return new R(RespBeanEnum.SUCCESS,replyVos);
    }
    /**
     * 删除回答
     * @param replyId：回答id
     * @return
     */
    @GetMapping("/delReply/{replyId}")
    public R delReply(@PathVariable("replyId")Long replyId){
        log.info("delReply:{}",replyId);
        boolean b = this.replyService.delReply(replyId);
        if (b)
            return new R(RespBeanEnum.REPLY_DELETE_SUCCESS,null);
        return new R(RespBeanEnum.REPLY_DELETE_ERROR,null);
    }

    /**
     * 查询问题的回答;逐个查询;
     * @param problemId 问题id
     * @param current 第几个回答;从1开始,不断增加即可
     * @return
     */
//    @SentinelResource(value = "searchReply")
    @GetMapping("/searchReply/{problemId}/{current}")
    public R searchReply(@PathVariable("problemId")Long problemId,@PathVariable("current")Long current){
        ReplyEntity replyEntity = this.replyService.searchReply(problemId, current);
        if (replyEntity == null)
            return new R(RespBeanEnum.SUCCESS,null);
        return new R(RespBeanEnum.SUCCESS,replyEntity);
    }

    /**
     * 查看当前用户的草稿箱
     * 返回当前用户的所有草稿回答
     * @return
     */
    @GetMapping("/searchDrafts")
    public R searchDrafts(){
        List<ReplyVo> replyVos = this.replyService.searchDrafts();
        return new R(RespBeanEnum.SUCCESS,replyVos);
    }

    /**
     * 根据回答的id查询回答的详细内容或草稿回答的详细内容
     * 注意：与通过问题逐个查询回答不一样的
     * @param replyId 回答id或草稿回答id（这2个其实都是回答表的一条记录，只是issue不同而已）
     * @return
     */
    @GetMapping("/searchReplyByReplyId/{replyId}")
    public R searchReplyByReplyId(@PathVariable("replyId")Long replyId){
        ReplyEntity replyEntity = this.replyService.searchReplyByReplyId(replyId);
        if (replyEntity == null)
            return new R(RespBeanEnum.REPLU_SEARCH_ERROR,null);
        return new R(RespBeanEnum.SUCCESS,replyEntity);
    }


}
