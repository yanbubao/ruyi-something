package com.example.demo.dubbo.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.example.demo.dubbo.constants.RequestContentConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * TransmittableThreadLocal是基于 ThreadLocal 和 InheritableThreadLocal 的基础上进行设计的一款组件，
 * 可以将 A 线程的本地变量透传到线程池内部的线程中。
 *
 * @Author yanzx
 * @Date 2022/11/17 22:53
 */
public class CommonRequestContext {

    private static final ThreadLocal<Map<RequestContentConstants, CommonRequest>> REQUEST_CONTENT_MAP =

            new TransmittableThreadLocal<Map<RequestContentConstants, CommonRequest>>() {
                @Override
                protected Map<RequestContentConstants, CommonRequest> initialValue() {
                    return new HashMap<>();
                }

                @Override
                public Map<RequestContentConstants, CommonRequest> copy(Map<RequestContentConstants, CommonRequest> parentValue) {
                    return parentValue != null ? new HashMap<>(parentValue) : null;
                }
            };

    public static void put(RequestContentConstants key, CommonRequest value) {
        REQUEST_CONTENT_MAP.get().put(key, value);
    }

    public static Object get(RequestContentConstants key) {
        return REQUEST_CONTENT_MAP.get().get(key);
    }

    public static void clear() {
        REQUEST_CONTENT_MAP.remove();
    }
}
