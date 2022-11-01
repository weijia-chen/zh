package com.weijia.zh.qa.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {

//    3个队列的名称
    public static final String  MESSAGE_FOLLOW = "message_follow";
    public static final String  MESSAGE_ME = "message_me";
    public static final String  MESSAGE_TOPIC = "message_topic";
    public static final String  MESSAGE_PROBLEM = "message_queue";

//    一个交换机
    public static final String MESSAGE = "message";

//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.setConnectionFactory(connectionFactory);
//        rabbitTemplate.setMandatory(true);//设置开启Mandatory，才能触发回调函数，无论消息的推送结果如何都强制调用回调函数
//
//        //确认消息送到交换机
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean b, String s) {
//                System.out.println("确认消息送到交换机。结果：");
//                System.out.println("相关数据："+ correlationData);
//                System.out.println("是否成功："+b);
//                System.out.println("错误原因："+s);
//
//            }
//        });
//
//        //确认消息送到队列
//        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback()
//        {
//            @Override
//            public void returnedMessage(ReturnedMessage returnedMessage)
//            {
//                System.out.println("\n确认消息送到队列(Queue)结果：");
//                System.out.println("发生消息：" + returnedMessage.getMessage());
//                System.out.println("回应码：" + returnedMessage.getReplyCode());
//                System.out.println("回应信息：" + returnedMessage.getReplyText());
//                System.out.println("交换机：" + returnedMessage.getExchange());
//                System.out.println("路由键：" + returnedMessage.getRoutingKey());
//            }
//        });
//        return rabbitTemplate;
//
//    }

//    创建交换机
    @Bean
    public Exchange message() {
        return ExchangeBuilder.directExchange(MESSAGE).durable(true).build();
    }

//    创建队列
    @Bean
    public Queue messageFollow(){
        return  new Queue(MESSAGE_FOLLOW,true,false,false);
    }
    @Bean
    public Queue messageMe(){
        return  new Queue(MESSAGE_ME,true,false,false);
    }
    @Bean
    public Queue messageTopic(){
        return  new Queue(MESSAGE_TOPIC,true,false,false);
    }
    @Bean
    public Queue messageProblem(){
        return  new Queue(MESSAGE_PROBLEM,true,false,false);
    }


//    绑定三个队列
    @Bean
    public Binding bindingFollow(@Qualifier("messageFollow")Queue queue,@Qualifier("message") Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(this.MESSAGE_FOLLOW).noargs();
    }
    @Bean
    public Binding bindingMe(@Qualifier("messageMe")Queue queue,@Qualifier("message") Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(this.MESSAGE_ME).noargs();
    }
    @Bean
    public Binding bindingTopic(@Qualifier("messageTopic")Queue queue,@Qualifier("message") Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(this.MESSAGE_TOPIC).noargs();
    }
    @Bean
    public Binding bindingProblem(@Qualifier("messageProblem")Queue queue,@Qualifier("message") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(this.MESSAGE_PROBLEM).noargs();
    }

//    mq消息序列化
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
