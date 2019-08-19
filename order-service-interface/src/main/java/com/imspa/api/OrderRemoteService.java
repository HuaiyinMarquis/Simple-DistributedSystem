package com.imspa.api;

import com.imspa.api.order.OrderDTO;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-08 16:27
 */
public interface OrderRemoteService {
    OrderDTO get(String id);
}
