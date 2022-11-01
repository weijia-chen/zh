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
import com.weijia.zh.qa.key.Key;
import com.weijia.zh.qa.service.RabbitmqProduct;
import com.weijia.zh.qa.service.ReplyService;
import com.weijia.zh.qa.utils.RedisUtil;
import com.weijia.zh.qa.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.weijia.zh.qa.dao.LikeDao;
import com.weijia.zh.qa.entity.LikeEntity;
import com.weijia.zh.qa.service.LikeService;

import javax.servlet.http.HttpSession;

@Slf4j
@Service("likeService")
public class LikeServiceImpl  implements LikeService {


    @Autowired
    private Key key;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private RabbitmqProduct rabbitmqProduct;

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private LikeDao likeDao;

    @Autowired
    @Qualifier("threadPoolTaskExecutor")//指定注入bean的名称
    private ThreadPoolTaskExecutor executor;

    /**
     1）回答的点赞使用String存储。key是：praise:id(id是回答的id)；value是回答的点赞数量
     2）当有人点赞时就令value++。
     3）当点赞数量达到多少的倍数就将点赞数量更新到数据库中。并设置该键的过期时间
     4）每个回答都维持一个set集合 。每个人点赞后就加入到一个set集合。
     5）点赞前，判断该人是否给该回答点赞过了。
     6）点赞后，将该用户的id加入该回答的set集合
     注：这个方案是有冗余的，完全可以通过set集合的长度得到该回答的点赞数量。但多个set不好遍历，而且还要开启定时任务写入数据库
     * @param replyId
     * @return
     */
    @Override
    public R likeReply(Long replyId) {

//      取到用户id
        UserEntity user = HandlerInterceptor.loginUser.get();
//        判断该人是否给该回答点赞过了
        boolean bl = this.redisUtil.sHasKey(this.key.getPraiseUser() + ":" + replyId, user.getId());

        if(!bl){//没点赞过
            long incr = this.redisUtil.incr(this.key.getPraise() + ":" + replyId, 1);//增加点赞数量
            if(incr%this.key.getPraiseSize() == 0){ //点赞数量是某数的倍数，更新数据库
                this.replyService.updateReplyLike(replyId,incr);//更新数据库的点赞数量
            }
            this.redisUtil.sSet(this.key.getPraiseUser()+":"+replyId,user.getId()); //将用户加入该点赞的集合

            CompletableFuture.runAsync(()->{
                //回答有人点赞,消息发给答主
//            先插入问题标题，id，用户id，用户昵称
                log.info("点赞消息发送");
                QueryWrapper<ProblemEntity> wrapper = new QueryWrapper<>();
                wrapper.inSql("id","SELECT tb_reply.problem_id FROM tb_reply where id="+replyId);
                wrapper.select("id","title","user_id");
                ProblemEntity problem = problemDao.selectOne(wrapper);
                log.info("查询到的问题:{}",problem);

//                查询答主id
                QueryWrapper wrapper1 = new QueryWrapper();
                wrapper1.select("user_id");
                wrapper1.eq("id",replyId);
                ReplyEntity replyEntity = this.replyDao.selectOne(wrapper1);

                MessageEntity message = new MessageEntity();

                message.setProblemId(problem.getId());
                message.setProblemTitle(problem.getTitle()); //问题标题，用于显示
                message.setUserId(replyEntity.getUserId()); //答主id，接收点赞消息的人

                message.setFromUserId(user.getId());//消息来源
                message.setFromUserName(user.getNickName());//消息来源
                message.setCreateTime(new Date());
                message.setKind(1);
                log.info("构造消息:{}",message);
                rabbitmqProduct.messageMe(message);
            },this.executor);

            return new R(RespBeanEnum.THUMP_UP_SUCCESS,111);
        }

        log.info("已经点过赞了....");
        return new R(RespBeanEnum.THUMP_UP_ERROR,null);
    }

    @Override
    public Long sumLikeByUserId(Long userId) {
        Long res = 0l;
        try {
            res = this.likeDao.sumLikeByUserId(userId);
        } catch (Exception e) {
            return 0l;
        }
        return res;
    }
}