package com.imspa.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-15 16:43
 */
@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queue.order}")
    private String orderQueueName;
    @Value("${rabbitmq.routingKey.order}")
    private String orderRoutingKey;


    @Value("${rabbitmq.queue.user}")
    private String userQueueName;
    @Value("${rabbitmq.routingKey.user}")
    private String userRoutingKey;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue orderQueue() {
        return new Queue(orderQueueName, false);
    }
    @Bean
    Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(exchange()).with(orderRoutingKey);
    }

    @Bean
    Queue userQueue() {
        return new Queue(userQueueName, false);
    }
    @Bean
    Binding userBinding() {
        return BindingBuilder.bind(userQueue()).to(exchange()).with(userRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
