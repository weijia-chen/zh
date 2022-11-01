package com.weijia.zh.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbitmq.client.Channel;
import com.weijia.zh.common.entity.MessageEntity;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.common.utils.RespData;
import com.weijia.zh.user.config.HandlerInterceptor;
import com.weijia.zh.user.dao.FollowDao;
import com.weijia.zh.user.dao.FollowTopicDao;
import com.weijia.zh.user.entity.UserEntity;
import com.weijia.zh.user.utils.RedisUtil;
import com.weijia.zh.user.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.weijia.zh.user.dao.MessageFollowDao;
import com.weijia.zh.user.entity.MessageFollowEntity;
import com.weijia.zh.user.service.MessageFollowService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Slf4j
@Service("messageFollowService")
public class MessageFollowServiceImpl implements MessageFollowService {

    @Value("${pageSize}")
    private Integer pageSize;

    @Autowired
    private MessageFollowDao messageFollowDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FollowDao followDao;


    @Override
    public R<RespData> searchMessageFollowByIdList(Integer current) {

//        先获得用户信息
        UserEntity user = HandlerInterceptor.loginUser.get();
        if(user == null)
            return  new R(RespBeanEnum.LOGIN_ADMINS_ERROR,null);
        log.info("user:{}",user);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        wrapper.orderByDesc("id");
        Page<MessageFollowEntity> page = new Page<>(current, this.pageSize);//实现分页
        this.messageFollowDao.selectPage(page,wrapper);
        List<MessageFollowEntity> messageFollowEntities = page.getRecords();
        Long total = page.getTotal();
        return new R<RespData>(RespBeanEnum.SUCCESS,new RespData(messageFollowEntities, total));

    }
    @RabbitListener(queues = {"message_follow"})
    @Transactional //批量提交
    public void conductMessage(Message message, MessageFollowEntity messageFollowEntity, Channel channel){
        log.info("message_follow关注的人有消息，消息:{}",messageFollowEntity);
        List<Long>  users = this.followDao.searchUserIdByTarget(messageFollowEntity.getFromUserId());
        log.info("=========关注的人有:{}",users);
        for (Long user : users) {
            messageFollowEntity.setId(null);
            messageFollowEntity.setUserId(user);
            this.messageFollowDao.insert(messageFollowEntity);
        }
        log.info("关注的人有消息处理完毕");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("应答出错message_follow");
        }

    }


}