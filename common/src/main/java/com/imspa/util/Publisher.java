package com.imspa.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-15 16:47
 */
public class Publisher<T> {
    private static final Logger logger = LogManager.getLogger(Publisher.class);
    private AmqpTemplate amqpTemplate;
    private String exchange;
    private String routingKey;

    public Publisher(AmqpTemplate amqpTemplate, String exchange, String routingKey) {
        this.amqpTemplate = amqpTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void produceMsg(T obj) {
        amqpTemplate.convertAndSend(exchange, routingKey, obj);
        logger.info("routingKey:{},send message:{}", routingKey, obj);
    }
}
