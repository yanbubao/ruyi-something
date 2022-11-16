package com.example.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池的关闭
 * JDK 的线程池内部提供了两个关闭方法，shutdownNow 和 shuwdown 方法。
 * <p>
 * shutdownNow 方法的解释是：线程池拒接收新提交的任务，同时立马关闭线程池，线程池里的任务不再执行。
 * <p>
 * shutdown 方法的解释是：线程池拒接收新提交的任务，同时等待线程池里的任务执行完毕后关闭线程池。
 *
 * @Author yanzx
 * @Date 2022/11/16 15:11
 */
public class ExecutorShutDownDemo {
    public static void testShutDown() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("[testShutDown] begin");
                    while (true) {
                    }
                } finally {
                    System.out.println("[testShutDown] end");
                }
            }
        });
        Thread.sleep(2000);
        executorService.shutdown();
        System.out.println("[testShutDown] shutdown");
    }

    public static void testShutDownNow() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("[testShutdownNow] begin");
                    while (true) {
                    }
                } finally {
                    System.out.println("[testShutdownNow] end");
                }
            }
        });
        Thread.sleep(2000);
        executorService.shutdownNow();
        System.out.println("[testShutdownNow] shutdownNow");
    }

    public static void main(String[] args) throws InterruptedException {
        //testShutDown();
        testShutDownNow();
    }
}
