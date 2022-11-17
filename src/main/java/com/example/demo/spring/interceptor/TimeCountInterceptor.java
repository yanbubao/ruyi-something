package com.example.demo.spring.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用ThreadLocal记录请求耗时
 *
 * @Author yanzx
 * @Date 2022/11/17 15:01
 */
@Configuration
public class TimeCountInterceptor implements HandlerInterceptor {

    static class CommonThreadLocal extends ThreadLocal<Long> {
        @Override
        protected Long initialValue() {
            return -1L;
        }
    }

    private static final CommonThreadLocal TIME_COUNT = new CommonThreadLocal();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("提前赋值的获取：" + TIME_COUNT.get());
        // ...
        TIME_COUNT.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long currentTime = System.currentTimeMillis();
        long startTime = TIME_COUNT.get();
        long timeUse = currentTime - startTime;
        System.out.println(Thread.currentThread().getName() + "耗时：" + timeUse + "ms");
        TIME_COUNT.remove();
    }
}
