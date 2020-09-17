package com.chintec.ikks.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 11:07
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 延迟交换机名称
     */

    public static final String DELAY_EXCHANGE_NAME = "delay.exchange";
    /**
     * 延迟队列名称
     */
    public static final String DELAY_QUEUE_NAME = "delay.queue";
    /**
     * 处理消息队列
     */
    public static final String MESSAGE_QUEUE_NAME = "message.queue";
    /**
     * 延时队列和延时交换机的链接路由key
     */
    public static final String DELAY_QUEUE_ROUTING_KEY = "delay.queue.routing.key";

    /**
     * 延时队列和延时交换机的链接路由key
     */
    public static final String DELAY_ROUTING_KEY = "delay.routing.key";


    /*@Autowired
    RabbitAdmin rabbitAdmin;*/

    // 声明延时Exchange
    @Bean("delayExchange")
    public TopicExchange delayExchange() {
        return new TopicExchange(DELAY_EXCHANGE_NAME);
    }


    // 声明延时队列并绑定到死信交换机
    @Bean("delayQueue")
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>(2);
        // x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DELAY_EXCHANGE_NAME);
        // x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DELAY_QUEUE_ROUTING_KEY);
        return QueueBuilder.durable(DELAY_QUEUE_NAME).withArguments(args).build();
    }

    /**
     * 声明消息队列并绑定到死信交换机
     */

    @Bean("messageQueue")
    public Queue messageQueue() {
        return QueueBuilder.durable(MESSAGE_QUEUE_NAME).build();
    }


    /**
     * 通过路由key链接Exchange和Queue
     *
     * @param exchange 交换机
     * @param queue    队列
     * @return
     */
    @Bean
    public Binding delayBinding(@Qualifier("delayQueue") Queue queue,
                                @Qualifier("delayExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_ROUTING_KEY);
    }


    // 声明消息队列绑定关系
    @Bean
    public Binding deadLetterBindingA(@Qualifier("messageQueue") Queue queue,
                                      @Qualifier("delayExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUE_ROUTING_KEY);
    }


    /**
     * 申明队列
     *
     * @return
     */
    /*@Bean
    public Queue queue() {
        return new Queue("message");
    }
    *//**
     * 申明交换机（主题模式）
     *
     * @return
     *//*
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    *//**
     * 将队列绑定到交换机
     *
     * @return
     *//*
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("topic.with");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public void createExchangeQueue() {
        rabbitAdmin.declareExchange(delayExchange());
        rabbitAdmin.declareQueue(delayQueue());
        rabbitAdmin.declareQueue(messageQueue());
    }*/
}
