package com.example.demo.thread.threadlocal;

import com.example.demo.spring.interceptor.TimeCountInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 拦截器实现统计请求耗时
 * <p>
 * 关于 InheritableThreadLocal 和 ThreadContextTransmitter
 * InheritableThreadLocal:
 * 主要用于子线程创建时，需要自动继承父线程的 ThreadLocal 变量，方便必要信息的进一步传递。
 * 具体原理是在创建子线程的“一刹那”，将父线程的 ThreadLocalMap 复制给子线程。但是在一些应用场景中，
 * 其功能并没有 ThreadContextTransmitter 强大。
 * <p>
 * ThreadContextTransmitter:
 * 它是基于 ThreadLocal 和 InheritableThreadLocal 的基础上进行设计的一款组件，
 * 可以将 A 线程的本地变量透传到线程池内部的线程中，在实现全链路追踪的时候经常会被用到。
 *
 * @Author yanzx
 * @Date 2022/11/17 15:00
 * @see TimeCountInterceptor
 */
@RestController
@RequestMapping(value = "/test")
public class ThreadLocalDemo {


    @GetMapping(value = "/do-test")
    public String doTest() throws InterruptedException {
        System.out.println("start do test");
        Thread.sleep(2000);
        System.out.println("end do test");
        return "success";
    }
}
