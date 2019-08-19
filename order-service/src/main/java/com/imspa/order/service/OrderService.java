package com.imspa.order.service;

import com.imspa.order.mapper.OrderMapper;
import com.imspa.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-10 19:54
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public Order get(String id) {
        return orderMapper.selectByPrimaryKey(id);
    }
}
