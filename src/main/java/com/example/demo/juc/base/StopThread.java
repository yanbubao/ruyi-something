package com.example.demo.juc.base;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 在 Thread 类中，提供了一个叫做 stop 的函数，这个方法有点类似于 Linux 操作系统中的 kill 指令，
 * 它的本质是直接终止线程，如果线程中持有某个锁对象，还会强制将该锁释放，从而可能导致该锁所保护的临界区缺少同步安全性
 *
 * @Author yanzx
 * @Date 2022/11/16 14:37
 */
public class StopThread {
    public static void main(String[] args) throws InterruptedException {
        Thread testThread = new Thread(new TestThread());
        testThread.start();
        Thread testThreadWithSync = new Thread(new TestThreadWithSync());
        testThreadWithSync.start();
        Thread testThreadWithLock = new Thread(new TestThreadWithLock());
        testThreadWithLock.start();

        Thread.sleep(2000);
        testThread.stop();
        testThreadWithSync.stop();
        testThreadWithLock.stop();
    }

    static class TestThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class TestThreadWithSync implements Runnable {

        @Override
        public void run() {
            synchronized (this) {
                for (int i = 20; i < 30; i++) {
                    System.out.print(i + " ");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class TestThreadWithLock implements Runnable {

        ReentrantLock reentrantLock = new ReentrantLock();

        @Override
        public void run() {
            reentrantLock.lock();
            try {
                for (int i = 30; i < 40; i++) {
                    System.out.print(i + " ");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }

}
