package com.chintec.ikks.common.util;

/**
 * MQ变量值
 *
 * @author rubin·lv
 * @version 1.0
 * @date 2020/9/18 10:35
 */
public class MqVariableUtil {

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

    /**
     * 普通交换机名称
     */
    public static final String TOPIC_EXCHANGE_NAME = "topicExchange";
    /**
     * 消息队列名称
     */
    public static final String MESSAGE = "message";

    /**
     * 消息模型队列
     */
    public static final String MESSAGE_MODEL_QUEUE = "message.model.queue";

    /**
     * routing_key
     */
    public static final  String TOPIC_WITH_ROUTING_KEY ="topic.with";
    /**
     * routing_key
     */
    public static final  String MESSAGE_MODEL_ROUTING_KEY = "message.model";
}
