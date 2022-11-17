package com.example.demo.juc.volatiledemo;

/**
 * @Author yanzx
 * @Date 2022/11/17 15:50
 */
public class LazyDclSingleton {
    private LazyDclSingleton() {
    }

    private static LazyDclSingleton instance;

    public static LazyDclSingleton getInstance() {
        if (instance == null) {
            synchronized (LazyDclSingleton.class) {
                if (instance == null) {
                    instance = new LazyDclSingleton();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(new ExecutorThread(), "t1");
        Thread t2 = new Thread(new ExecutorThread(), "t2");

        t1.start();
        t2.start();
    }
}
