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
public class OrderSubscriber {
    private static final Logger logger = LogManager.getLogger(OrderSubscriber.class);

    @RabbitListener(queues = "${rabbitmq.queue.order}")
    public void receiveMessage(Object obj) {
        logger.info("order subscribe message:{}",obj);
    }
}
