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
 * @Description config resource RPC service
 * @Date 2019-08-09 16:10
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
