package com.imspa.order.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-15 17:11
 */
@Component
public class UserSubscriber {
    private static final Logger logger = LogManager.getLogger(UserSubscriber.class);

    @RabbitListener(queues = "${rabbitmq.queue.user}")
    public void receiveMessage(Object obj) {
        logger.info("user subscribe message:{}",obj);
    }
}
