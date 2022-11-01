package com.weijia.zh.qa.service.impl;

import com.weijia.zh.common.entity.MessageEntity;
import com.weijia.zh.common.entity.UserEntity;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.qa.config.HandlerInterceptor;
import com.weijia.zh.qa.dao.ProblemDao;
import com.weijia.zh.qa.dao.ReplyDao;
import com.weijia.zh.qa.entity.ProblemEntity;
import com.weijia.zh.qa.entity.ReplyEntity;
import com.weijia.zh.qa.service.RabbitmqProduct;
import com.weijia.zh.qa.utils.SentimentAnalysis;
import com.weijia.zh.qa.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.weijia.zh.qa.dao.CommentDao;
import com.weijia.zh.qa.entity.CommentEntity;
import com.weijia.zh.qa.service.CommentService;

import javax.servlet.http.HttpSession;

@Slf4j
@Service("commentService")
public class CommentServiceImpl  implements CommentService {


    @Autowired
    private CommentDao commentDao;
    @Autowired
    @Qualifier("threadPoolTaskExecutor")//指定注入bean的名称
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private RabbitmqProduct rabbitmqProduct;

    @Override
    public R searchCommentsByReplyId(Long replyId) {
        QueryWrapper<CommentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("reply_id",replyId);
        wrapper.orderByDesc("create_time");
        List<CommentEntity> commentEntities = this.commentDao.selectList(wrapper);
        return new R(RespBeanEnum.SUCCESS,commentEntities);
    }

    /**
     * 1、得到用户信息（注意使用json转换格式）
     * 2、完善评论信息（时间，用户，回答id）
     * 3、插入数据库
     * 4、插入成功返回该条评论
     * @return
     */
    @Override
    public R insertComment(CommentEntity comment) {

        UserEntity user = HandlerInterceptor.loginUser.get();

        comment.setUserId(user.getId());
        comment.setUserName(user.getNickName());
        comment.setUserAvatar(user.getAvatar());
        comment.setCreateTime(new Date());
        int mood = SentimentAnalysis.sentimentAnalysis(comment.getContent());
        comment.setMood(mood);
        log.info("评论为：{}",comment);
        int insert = 0;
        try {
            insert = this.commentDao.insert(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(RespBeanEnum.COMMENT_ERROR,null);
        }

        if (insert <= 0)
            return new R(RespBeanEnum.COMMENT_ERROR,null);


        CompletableFuture.runAsync(()->{
            //回答有人评论,消息发给答主
//            先插入问题标题，id，用户id，用户昵称
            log.info("评论消息发送");
            QueryWrapper<ProblemEntity> wrapper = new QueryWrapper<>();
            wrapper.inSql("id","SELECT tb_reply.problem_id FROM tb_reply where id="+comment.getReplyId());
            wrapper.select("id","title","user_id");
            ProblemEntity problem = problemDao.selectOne(wrapper);
            log.info("查询到的问题:{}",problem);

//                查询答主id
            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.select("user_id");
            wrapper1.eq("id",comment.getReplyId());
            ReplyEntity replyEntity = this.replyDao.selectOne(wrapper1);

            MessageEntity message = new MessageEntity();

            message.setProblemId(problem.getId());
            message.setProblemTitle(problem.getTitle()); //问题标题，用于显示
            message.setUserId(replyEntity.getUserId()); //答主id，接收点赞消息的人

            message.setFromUserId(user.getId());//消息来源
            message.setFromUserName(user.getNickName());//消息来源
            message.setCreateTime(new Date());
            message.setKind(2);
            log.info("构造消息:{}",message);
            rabbitmqProduct.messageMe(message);
        },this.executor);


        return  new R(RespBeanEnum.COMMENT_SUCCESS,comment);
    }
}