package com.chintec.ikks.auth.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelayQueue {
    /** 消息交换机的名字*/
    public static final String EXCHANGE = "delay.exchange";
    /** 队列key1*/
    public static final String DELAY_ROUTING_KEY = "delay_routing_key";
    /** 队列key2*/
    public static final String DELAY_QUEUE_ROUTING_KEY = "delay.queue.routingkey";

    public static final String MESSAGE_QUEUE_NAME = "message.queue";
    /**
     * 配置链接信息
     * @return
     */
    @Bean
    public ConnectionFactory rabbitMqConnection() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("182.92.65.228",5672);

        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true); // 必须要设置
        return connectionFactory;
    }

    /**
     * 配置消息交换机
     * 针对消费者配置
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    /**
     * 配置消息队列2
     * 针对消费者配置
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(MESSAGE_QUEUE_NAME, true); //队列持久
    }
    /**
     * 将消息队列2与交换机绑定
     * 针对消费者配置
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(DelayQueue.DELAY_QUEUE_ROUTING_KEY);
    }

    /**
     * 接受消息的监听，这个监听会接受消息队列1的消息
     * 针对消费者配置
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer2() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(rabbitMqConnection());
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {

            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("delay_queue2 收到消息 : " + new String(body));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费

            }

        });
        return container;
    }
}
