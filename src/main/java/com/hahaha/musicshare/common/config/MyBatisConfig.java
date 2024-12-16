package com.hahaha.musicshare.common.config;

import com.github.yulichang.interceptor.MPJInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.hahaha.musicshare.mapper"})
public class MyBatisConfig {

    /**
     * 配置 MPJInterceptor（MyBatis-Plus-Join 拦截器）
     */
    @Bean
    public MPJInterceptor mpjInterceptor() {
        return new MPJInterceptor();
    }

}