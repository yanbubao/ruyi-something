package com.example.demo.thread;

/**
 * 使用了一个ShutdownHook的钩子函数，用于监听当前JVM是否退出
 *
 * @author yanzx
 * @date 2022/11/15 23:37
 */
public class DaemonThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("jvm exit success.")));

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    System.out.println("thread still running ....");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // 守护线程具有在JVM退出的时候也自我销毁的特点，而非守护线程不具备这个特点，这也是为什么GC回收线程被设置为守护线程类型的主要原因
        thread.setDaemon(true);
        thread.start();
        // > 2000
        Thread.sleep(3000);
    }
}
