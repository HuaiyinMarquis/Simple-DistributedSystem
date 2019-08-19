package com.imspa.order.web;

import com.imspa.api.OrderRemoteService;
import com.imspa.api.order.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-09 16:50
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderRemoteService orderService;

    @GetMapping("/get/{id}")
    public OrderDTO get(@PathVariable("id")String id) {
        return orderService.get(id);
    }
}
