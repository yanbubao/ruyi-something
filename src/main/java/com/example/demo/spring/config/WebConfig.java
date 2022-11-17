package com.example.demo.spring.config;

import com.example.demo.spring.interceptor.TimeCountInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * @Author yanzx
 * @Date 2022/11/17 15:16
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Resource
    private TimeCountInterceptor timeCountInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.timeCountInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
