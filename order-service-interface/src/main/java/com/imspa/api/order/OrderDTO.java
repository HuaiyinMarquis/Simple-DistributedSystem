package com.imspa.api.order;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-08 16:27
 */
@Data
@Accessors(chain = true)
public class OrderDTO implements Serializable {
    private String orderId;
    private String userId;
    private String productId;
    private String productName;
    private BigDecimal orderPrice;
    private String status;
    private Integer isDeleted;
}
