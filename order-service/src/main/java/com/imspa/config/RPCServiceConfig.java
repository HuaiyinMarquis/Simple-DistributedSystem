package com.imspa.config;

import com.imspa.rpc.core.RPCRecvExecutor;
import com.imspa.rpc.model.RPCInterfacesWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pann
 * @description config order server's RPC service method
 * @date 2019-08-08 14:51
 */
@Configuration
@EnableConfigurationProperties
public class RPCServiceConfig {
    @Value("${service.addr}")
    private String addr;

    @Bean
    @ConfigurationProperties(prefix = "service")
    public RPCInterfacesWrapper serviceContainer() {
        return new RPCInterfacesWrapper();
    }

    @Bean
    public RPCRecvExecutor recvExecutor() {
        return new RPCRecvExecutor(addr);
    }

}
