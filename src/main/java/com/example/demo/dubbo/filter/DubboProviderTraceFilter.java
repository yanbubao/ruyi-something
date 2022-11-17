package com.example.demo.dubbo.filter;

import com.alibaba.fastjson.JSON;
import com.example.demo.dubbo.constants.RequestContentConstants;
import com.example.demo.dubbo.context.CommonRequest;
import com.example.demo.dubbo.context.CommonRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @Author yanzx
 * @Date 2022/11/17 23:09
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class DubboProviderTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            String attachment = invocation.getAttachment(RequestContentConstants.COMMON_REQUEST.name());
            if (StringUtils.isNotBlank(attachment)) {
                CommonRequest commonRequest = JSON.parseObject(attachment, CommonRequest.class);
                CommonRequestContext.put(RequestContentConstants.COMMON_REQUEST, commonRequest);
                log.info("[DubboConsumerTraceFilter] ------> setValue in map");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return invoker.invoke(invocation);
    }
}
