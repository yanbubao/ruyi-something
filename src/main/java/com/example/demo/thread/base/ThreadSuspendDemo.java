package com.example.demo.thread.base;

import java.util.concurrent.TimeUnit;

/**
 * @Author yanzx
 * @Date 2022/11/16 14:26
 */
public class ThreadSuspendDemo {
    /**
     * 暂停线程
     */
    static class SuspendThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                System.out.print(i + " ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread suspendThread = new Thread(new SuspendThread());
        suspendThread.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.print("线程暂停");
        //暂停线程
        suspendThread.suspend();// notice 线程虽然暂停了，但是如果该线程曾经持有过锁并且也未曾主动释放过锁的话，那么这个处于暂停状态的线程就会一直持有锁
        TimeUnit.SECONDS.sleep(2);
        //从之前暂停的位置恢复继续执行
        suspendThread.resume();// notice 线程虽然暂停了，但是如果该线程曾经持有过锁并且也未曾主动释放过锁的话，那么这个处于暂停状态的线程就会一直持有锁
        System.out.print(" 线程恢复");
    }
}
