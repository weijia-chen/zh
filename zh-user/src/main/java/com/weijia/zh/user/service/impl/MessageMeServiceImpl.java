package com.weijia.zh.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbitmq.client.Channel;
import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.R;
import com.weijia.zh.common.utils.RespData;
import com.weijia.zh.user.config.HandlerInterceptor;
import com.weijia.zh.user.dao.UserDao;
import com.weijia.zh.user.entity.MessageFollowEntity;
import com.weijia.zh.user.entity.MessageTopicEntity;
import com.weijia.zh.user.entity.UserEntity;
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


import com.weijia.zh.user.dao.MessageMeDao;
import com.weijia.zh.user.entity.MessageMeEntity;
import com.weijia.zh.user.service.MessageMeService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Slf4j
@Service("messageMeService")
public class MessageMeServiceImpl implements MessageMeService {

    @Autowired
    private MessageMeDao messageMeDao;

    @Autowired
    private UserDao userDao;

    @Value("${pageSize}")
    private Integer pageSize;

    @Override
    public R<RespData> searchMessageMe(Integer current) {
        //        先获得用户信息
        UserEntity user = HandlerInterceptor.loginUser.get();
        if(user == null)
            return  new R(RespBeanEnum.LOGIN_ADMINS_ERROR,null);
        log.info("user:{}",user);
        //消息通知红点消除
        this.userDao.updateAdvice(user.getId(), 0);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        wrapper.orderByDesc("create_time");
        Page<MessageMeEntity> page = new Page<>(current, this.pageSize);//实现分页
        this.messageMeDao.selectPage(page, wrapper);
        List<MessageMeEntity> records = page.getRecords();
        long total = page.getTotal();
        return new R<RespData>(RespBeanEnum.SUCCESS,new RespData(records, total));
    }

    @RabbitListener(queues = {"message_me"})
    public void conductMessage(Message message, MessageMeEntity messageMeEntity, Channel channel){

        log.info("message_me我有一条消息:{}",messageMeEntity);
        this.messageMeDao.insert(messageMeEntity);
        //记录未接收的通知
        this.userDao.updateAdvice(messageMeEntity.getUserId(),1);
        log.info("我有一条消息处理完毕");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}