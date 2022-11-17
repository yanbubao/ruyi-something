package com.example.demo.thread.volatiledemo;

/**
 * @Author yanzx
 * @Date 2022/11/17 15:52
 */
public class VolatileDemo {
    /**
     * volatile在JVM层面会根据不同硬件（CPU架构）和OS去生成不同的"内存屏障指令"
     */
    private volatile int a = 0;


    /**
     * Java中能够创建volatile数组吗？
     * 可以创建！
     * Volatile对于引用可见，对于数组中的元素不具备可见性
     */
    private static volatile Object[] object = new Object[10];

    public static void main(String[] args) {
        object[0] = 1; // 无lock指令
        object = new Object[2];// 字节码中有lock指令
    }

}
