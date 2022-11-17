package com.example.demo.juc.traceIdCase.consumer.config;

import com.example.demo.juc.traceIdCase.consumer.interceptor.ChannelFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author yanzx
 * @Date 2022/11/17 23:18
 */
@Configuration
public class TraceFilterConfig implements WebMvcConfigurer {
    @Bean
    public ChannelFilter channelFilter(){
        return new ChannelFilter();
    }
}
