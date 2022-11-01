package com.weijia.zh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbitmq.client.Channel;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.common.utils.RespData;
import com.weijia.zh.user.config.HandlerInterceptor;
import com.weijia.zh.user.dao.FollowProblemDao;
import com.weijia.zh.user.dao.MessageProblemDao;
import com.weijia.zh.user.entity.*;
import com.weijia.zh.user.service.MessageProblemService;
import com.weijia.zh.user.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MessageProblemServiceImpl implements MessageProblemService {

    @Autowired
    private FollowProblemDao followProblemDao;

    @Autowired
    private MessageProblemDao messageProblemDao;
    @Value("${pageSize}")
    private Integer pageSize;
    @Override
    public R<RespData> searchMessageProblems(Integer current) {
        //        先获得用户信息
        UserEntity user = HandlerInterceptor.loginUser.get();
        if(user == null)
            return  new R(RespBeanEnum.LOGIN_ADMINS_ERROR,null);
        log.info("user:{}",user);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        wrapper.orderByDesc("id");
        Page<MessageProblem> page = new Page<>(current,this.pageSize);
        this.messageProblemDao.selectPage(page,wrapper);
        List<MessageProblem> messageProblems = page.getRecords();
        long total = page.getTotal();

        return new R<RespData>(RespBeanEnum.SUCCESS,new RespData(messageProblems,total));
    }

    @RabbitListener(queues = {"message_queue"})
    @Transactional //批量提交
    public void conductMessage(Message message, MessageProblem messageProblem, Channel channel){
        log.info("======message_queue问题的处理，消息:{}",messageProblem);
//        1.查所有关注该问题的用户
        QueryWrapper<FollowProblem> wrapper = new QueryWrapper<>();
        wrapper.eq("problem_id",messageProblem.getProblemId());
        wrapper.select("user_id");
        List<FollowProblem> list = this.followProblemDao.selectList(wrapper);
        log.info("===========查询得到关注该问题题的list",list);
        for (FollowProblem followProblem : list) {
            messageProblem.setId(null);
            messageProblem.setUserId(followProblem.getUserId());
            this.messageProblemDao.insert(messageProblem);
        }
        log.info("=============问题提问处理完毕");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
