package com.example.demo.thread.threadlocal;

import com.example.demo.spring.interceptor.TimeCountInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 拦截器实现统计请求耗时
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
