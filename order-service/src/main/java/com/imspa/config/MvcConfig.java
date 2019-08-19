package com.imspa.config;

import com.imspa.web.auth.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-16 12:15
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource(name = "redisTemplate")
    private HashOperations<String, byte[], byte[]> hashOperations;

    @Bean
    public HashMapper<Object, byte[], byte[]> hashMapper() {
        return new ObjectHashMapper();
    }

    @Bean
    public Filter securityFilter() {
        return new SecurityFilter(hashOperations, hashMapper());
    }
}
