package com.example.demo.dubbo.filter;

import com.alibaba.fastjson.JSON;
import com.example.demo.dubbo.constants.RequestContentConstants;
import com.example.demo.dubbo.context.CommonRequest;
import com.example.demo.dubbo.context.CommonRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * 在发起 Dubbo 调用之前，请求会先通过 Dubbo 的过滤器层，当请求抵达 Dubbo 的新节点时，也会先通过过滤器层
 *
 * @Author yanzx
 * @Date 2022/11/17 23:02
 */
@Slf4j
@Activate(group = CommonConstants.CONSUMER)
public class DubboConsumerTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Object o = CommonRequestContext.get(RequestContentConstants.COMMON_REQUEST);

        try {
            if (o instanceof CommonRequest) {
                CommonRequest commonRequest = (CommonRequest) o;
                log.info("[DubboConsumerTraceFilter] ------> commonRequest is {}", commonRequest);
                // 注入到Dubbo的上下文中
                invocation.getAttachment(RequestContentConstants.COMMON_REQUEST.name(), JSON.toJSONString(commonRequest));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return invoker.invoke(invocation);
    }
}
