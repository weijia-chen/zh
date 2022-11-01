package com.weijia.zh.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbitmq.client.Channel;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.common.utils.RespData;
import com.weijia.zh.user.config.HandlerInterceptor;
import com.weijia.zh.user.dao.FollowTopicDao;
import com.weijia.zh.user.entity.FollowTopic;
import com.weijia.zh.user.entity.MessageFollowEntity;
import com.weijia.zh.user.entity.UserEntity;
import com.weijia.zh.user.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.weijia.zh.user.dao.MessageTopicDao;
import com.weijia.zh.user.entity.MessageTopicEntity;
import com.weijia.zh.user.service.MessageTopicService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Slf4j
@Service("messageTopicService")
public class MessageTopicServiceImpl implements MessageTopicService {

    @Value("${pageSize}")
    private Integer pageSize;

    @Autowired
    private MessageTopicDao messageTopicDao;

    @Autowired
    private FollowTopicDao followTopicDao;

    @Override
    public R<RespData> searchMessageTopics(Integer current) {
        //        先获得用户信息
//        UserEntity user = JsonPare.pare(session.getAttribute(userTicket),UserEntity.class);
        UserEntity user = HandlerInterceptor.loginUser.get();
        if(user == null)
            return  new R(RespBeanEnum.LOGIN_ADMINS_ERROR,null);
        log.info("user:{}",user);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        wrapper.orderByDesc("create_time");
        Page<MessageTopicEntity> page = new Page<>(current, this.pageSize);//实现分页
       this.messageTopicDao.selectPage(page, wrapper);

        List<MessageTopicEntity> records = page.getRecords();
        long total = page.getTotal();

        return new R<RespData>(RespBeanEnum.SUCCESS,new RespData(records, total));
    }

    @RabbitListener(queues = {"message_topic"})
    @Transactional //批量提交
    public void conductMessage(Message message, MessageTopicEntity messageTopic, Channel channel){
        log.info("======话题提出问题的处理，消息:{}",messageTopic);

//        1.查所有关注该话题的用户
        QueryWrapper<FollowTopic> wrapper = new QueryWrapper<>();
        wrapper.eq("topic_id",messageTopic.getTopicId());
        wrapper.select("user_id");

        List<FollowTopic> list = this.followTopicDao.selectList(wrapper);
        log.info("===========查询得到关注该话题的list",list);
        for (FollowTopic userTopic : list) {
            messageTopic.setId(null);
            messageTopic.setUserId(userTopic.getUserId());
            this.messageTopicDao.insert(messageTopic);
        }
        log.info("=============话题提问处理完毕");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}