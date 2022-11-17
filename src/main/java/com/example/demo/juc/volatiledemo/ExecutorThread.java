package com.example.demo.juc.volatiledemo;

/**
 * @Author yanzx
 * @Date 2022/11/17 15:51
 */
public class ExecutorThread implements Runnable {
    @Override
    public void run() {
        LazyDclSingleton instance = LazyDclSingleton.getInstance();

        System.out.println(Thread.currentThread().getName() + ":" + instance);

        Object o = new Object();

    }
}

