package com.weijia.zh.qa.service.impl;

import com.weijia.zh.common.entity.MessageEntity;
import com.weijia.zh.qa.config.RabbitmqConfig;
import com.weijia.zh.qa.service.RabbitmqProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitmqProductImpl implements RabbitmqProduct {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public boolean messageFollow(MessageEntity message) {
        log.info("消息.....");
        rabbitTemplate.convertAndSend(RabbitmqConfig.MESSAGE,RabbitmqConfig.MESSAGE_FOLLOW,message);
        log.info("消息发送出去:{}",message);
        return true;
    }

    @Override
    public boolean messageMe(MessageEntity message) {
        rabbitTemplate.convertAndSend(RabbitmqConfig.MESSAGE,RabbitmqConfig.MESSAGE_ME,message);
        log.info("messageMe:{}",message);
        return true;
    }

    @Override
    public boolean messageTopic(MessageEntity message) {
        rabbitTemplate.convertAndSend(RabbitmqConfig.MESSAGE,RabbitmqConfig.MESSAGE_TOPIC,message);
        log.info("messageTopic:{}",message);
        return true;
    }

    @Override
    public boolean messageProblem(MessageEntity message) {
        rabbitTemplate.convertAndSend(RabbitmqConfig.MESSAGE,RabbitmqConfig.MESSAGE_PROBLEM,message);
        log.info("messageProblem:{}",message);
        return true;
    }
}
