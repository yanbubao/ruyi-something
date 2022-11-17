package com.example.demo.juc.base;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 在 Thread 类里面，提供了一个叫做 interrupt ****的函数，
 * 这个函数的名字翻译过来是中断的意思，但是它实际上并不是真正中断，只是将指定线程的状态调整为了中断类型
 * <p>
 * 如果线程已经标记为了 interrupt 状态，在线程调用了sleep,join,wait,condition.await 方法的时候会抛出 InterruptedException 异常。
 *
 * @Author yanzx
 * @Date 2022/11/16 15:08
 */
public class InterruptThread {
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


    static class ForEverThread implements Runnable {

        @Override
        public void run() {
            System.out.println("开始执行");
            while (true) {

            }
        }


    }


    public static void main(String[] args) throws InterruptedException {
        Thread testThread = new Thread(new TestThread());
        testThread.start();
        Thread testThreadWithSync = new Thread(new TestThreadWithSync());
        testThreadWithSync.start();
        Thread testThreadWithLock = new Thread(new TestThreadWithLock());
        testThreadWithLock.start();
        Thread forEverThread = new Thread(new ForEverThread());
        forEverThread.start();

        Thread.sleep(2000);

        forEverThread.interrupt();
        testThread.interrupt();
        testThreadWithSync.interrupt();
        testThreadWithLock.interrupt();

    }
}
