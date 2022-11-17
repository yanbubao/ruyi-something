package com.example.demo.dubbo.utils;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.example.demo.dubbo.constants.RequestContentConstants;
import com.example.demo.dubbo.context.CommonRequest;
import com.example.demo.dubbo.context.CommonRequestContext;

/**
 * @Author yanzx
 * @Date 2022/11/17 23:31
 */
public class TraceInfoConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        CommonRequest request = (CommonRequest) CommonRequestContext.get(RequestContentConstants.COMMON_REQUEST);
        return request == null ? null : convertFromRequest(request);
    }

    public String convertFromRequest(CommonRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("traceId:").append(request.getTraceId())
                .append(",").append("userId:").append(request.getUserId());
        return builder.toString();
    }
}

