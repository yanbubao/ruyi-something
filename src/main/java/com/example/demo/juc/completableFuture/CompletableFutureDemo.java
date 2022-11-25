package com.example.demo.juc.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 使用CompletableFuture的底层原理
 *
 * @Author yanzx
 * @Date 2022/11/17 18:17
 */
public class CompletableFutureDemo {


    @Test
    public void whenCompleteAndExceptionally() {

        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
            if (Math.random() < 0.5) {
                throw new RuntimeException("error msg: < 0.5");
            }
            System.out.println("结果正常");
            return 0.11;
        }).whenComplete((aDouble, throwable) -> {

            if (aDouble == null) {
                System.out.println("when complete aDouble is null");
            } else {
                System.out.println("when complete aDouble is " + aDouble);
            }
            if (throwable == null) {
                System.out.println("when complete throwable is null");
            } else {
                System.out.println("when complete throwable is " + throwable.getMessage());
            }

        }).exceptionally(throwable -> {
            System.out.println("exceptionally中异常：" + throwable.getMessage());
            return null;
        });


        try {
            System.out.println("最终返回的结果： " + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void supplyAsyncAndRunAsync() {
        //supplyAsync带有返回结果
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "test";
            }
        });
        //runAsync不带有返回结果
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        });
    }

    @Test
    public void completeAndExceptionallyWithForkJoinPool() {
        ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
            return 1.2;
        }, pool);
        System.out.println("main thread start,time->" + System.currentTimeMillis());
    }

    @Test
    public void supplyAsyncAndThenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(Thread.currentThread().getName());
                return "test";
            }
        });

        CompletableFuture<String> completableFuture1 = completableFuture.thenApply(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println(s);
                System.out.println(Thread.currentThread().getName());
                return "idea";
            }
        });
        CompletableFuture.allOf();
        System.out.println(completableFuture1.get());;
        //CompletableFuture.anyOf();
    }

    @Test
    public void testGetAndJoin() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(1 / 0);
                return "success";
            }
        });
       // String result = completableFuture.get();
        String result2 = completableFuture.join();
    }
}
