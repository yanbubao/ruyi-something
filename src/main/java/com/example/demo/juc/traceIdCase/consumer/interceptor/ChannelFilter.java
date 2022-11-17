package com.example.demo.juc.traceIdCase.consumer.interceptor;

import com.example.demo.dubbo.constants.RequestContentConstants;
import com.example.demo.dubbo.context.CommonRequest;
import com.example.demo.dubbo.context.CommonRequestContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author yanzx
 * @Date 2022/11/17 22:40
 */
@Slf4j
public class ChannelFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Long userId = convertUserId(httpServletRequest);
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setTraceId(UUID.randomUUID().toString());
        commonRequest.setUserId(userId);
        CommonRequestContext.put(RequestContentConstants.COMMON_REQUEST, commonRequest);
        log.info("CommonRequest is {}", commonRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private static Long convertUserId(HttpServletRequest request) {
        String userIdStr = request.getHeader("user-id");
        if (userIdStr == null) {
            return -1L;
        }
        return Long.valueOf(userIdStr);
    }
}
