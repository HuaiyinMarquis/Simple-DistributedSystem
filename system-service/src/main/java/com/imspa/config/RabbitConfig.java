package com.imspa.config;

import com.imspa.api.order.OrderDTO;
import com.imspa.api.user.UserVO;
import com.imspa.util.MessagePublisherWrapper;
import org.springframework.amqp.core.AmqpTemplate;
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

    @Value("${rabbitmq.routingKey.user}")
    private String userRoutingKey;

    @Value("${rabbitmq.routingKey.order}")
    private String orderRoutingKey;

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

    @Bean
    public MessagePublisherWrapper userMessagePublisher(AmqpTemplate amqpTemplate) { //define useful publisher bean self
        return new MessagePublisherWrapper<UserVO>(amqpTemplate, exchange, userRoutingKey);
    }

    @Bean
    public MessagePublisherWrapper orderMessagePublisher(AmqpTemplate amqpTemplate) { //define useful publisher bean self
        return new MessagePublisherWrapper<OrderDTO>(amqpTemplate, exchange, orderRoutingKey);
    }
}
