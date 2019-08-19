package com.imspa.rpc;

import com.imspa.api.OrderRemoteService;
import com.imspa.api.order.OrderDTO;
import com.imspa.order.model.Order;
import com.imspa.order.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-08 16:26
 */
@Component
public class OrderRemoteServiceImpl implements OrderRemoteService {
    private static final Logger logger = LogManager.getLogger(OrderRemoteServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO get(String id) {
        Order order = orderService.get(id);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId()).setProductId(order.getProductId())
                .setProductName(order.getProductName()).setOrderPrice(order.getOrderPrice())
                .setUserId(order.getUserId()).setStatus(order.getStatus()).setIsDeleted(order.getIsDeleted());
        return orderDTO;
    }
}
