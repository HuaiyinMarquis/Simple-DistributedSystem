package com.imspa.config;

import com.imspa.api.OrderRemoteService;
import com.imspa.rpc.core.RPCSendExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-09 18:08
 */
@Configuration
public class RPCReferenceConfig {
    @Bean
    public RPCSendExecutor orderService() {
        return new RPCSendExecutor<OrderRemoteService>(OrderRemoteService.class,"localhost:8091");
    }
}
