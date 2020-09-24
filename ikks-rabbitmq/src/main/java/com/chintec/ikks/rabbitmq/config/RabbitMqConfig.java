package com.chintec.ikks.rabbitmq.config;

import com.chintec.ikks.common.util.MqVariableUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;


/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/8/20 11:07
 */
@Configuration
public class RabbitMqConfig {

    @Autowired
    RabbitAdmin rabbitAdmin;

    /**
     * 声明延时Exchange
     */
    @Bean("delayExchange")
    public TopicExchange delayExchange() {
        return new TopicExchange(MqVariableUtil.DELAY_EXCHANGE_NAME);
    }


    /**
     * 声明延时队列并绑定到死信交换机
     */
    @Bean("delayQueue")
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>(2);
        // x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", MqVariableUtil.DELAY_EXCHANGE_NAME);
        // x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", MqVariableUtil.DELAY_QUEUE_ROUTING_KEY);
        return QueueBuilder.durable(MqVariableUtil.DELAY_QUEUE_NAME).withArguments(args).build();
    }

    /**
     * 声明消息队列并绑定到死信交换机
     */

    @Bean("messageQueue")
    public Queue messageQueue() {
        return QueueBuilder.durable(MqVariableUtil.MESSAGE_QUEUE_NAME).build();
    }


    /**
     * 通过路由key链接Exchange和Queue
     *
     * @param exchange 交换机
     * @param queue    队列
     * @return Binding
     */
    @Bean
    public Binding delayBinding(@Qualifier("delayQueue") Queue queue,
                                @Qualifier("delayExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MqVariableUtil.DELAY_ROUTING_KEY);
    }


    /**
     * 声明消息队列绑定关系
     */
    @Bean
    public Binding deadLetterBindingA(@Qualifier("messageQueue") Queue queue,
                                      @Qualifier("delayExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MqVariableUtil.DELAY_QUEUE_ROUTING_KEY);
    }


    /**
     * 申明队列
     *
     * @return Queue
     */
    @Bean
    public Queue queue() {
        return new Queue(MqVariableUtil.MESSAGE);
    }

    /**
     * 申明队列
     *
     * @return Queue
     */
    @Bean
    public Queue messageModelQueue() {
        return new Queue(MqVariableUtil.MESSAGE_MODEL_QUEUE);
    }

    /**
     * 申明交换机（主题模式）
     *
     * @return TopicExchange
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MqVariableUtil.TOPIC_EXCHANGE_NAME);
    }

    /**
     * 将队列绑定到交换机
     *
     * @return Binding
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(MqVariableUtil.TOPIC_EXCHANGE_NAME);
    }

    /**
     * 将队列绑定到交换机
     *
     * @return Binding
     */
    @Bean
    public Binding bindingModelQueue() {
        return BindingBuilder.bind(messageModelQueue()).to(topicExchange()).with(MqVariableUtil.MESSAGE_MODEL_ROUTING_KEY);
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
    }

    @Bean
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setMessageConverter(new SerializerMessageConverter());
        return template;
    }
}
