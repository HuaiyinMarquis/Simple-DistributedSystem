package com.imspa.util;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-15 18:37
 */
public class MessagePublisherWrapper<T> implements FactoryBean {
    private AmqpTemplate amqpTemplate;
    private String exchange;
    private String routingKey;

    public MessagePublisherWrapper(AmqpTemplate amqpTemplate, String exchange, String routingKey) {
        this.amqpTemplate = amqpTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public Object getObject() throws Exception {
        return new Publisher<T>(amqpTemplate, exchange, routingKey);
    }

    @Override
    public Class<?> getObjectType() {
        return Publisher.class;
    }

}
