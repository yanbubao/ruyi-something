package com.example.demo.juc.lock;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yanzx
 * @Date 2022/11/16 20:07
 */
public class LockInterruptDemo {
    static class LockInterruptThread_1 implements Runnable {
        private ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            try {
                // notice lockInterruptibly 允许在等待时，由其它线程调用等待线程的 Thread.interrupt 方法来中断等待线程的等待
                lock.lockInterruptibly();
                try {
                    System.out.println("enter");
                    long startTime = System.currentTimeMillis();
                    for (; ; ) {
                        if (System.currentTimeMillis() - startTime >= 5000) {
                            break;
                        }
                    }
                    System.out.println("end");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock.isLocked()) {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class LockInterruptThread_2 implements Runnable {
        @Override
        public void run() {
            synchronized (this) {
                System.out.println("enter");
                long startTime = System.currentTimeMillis();
                for (; ; ) {
                    if (System.currentTimeMillis() - startTime >= 5000) {
                        break;
                    }
                }
                System.out.println("end");
            }
        }
    }

    static class LockInterruptThread_3 implements Runnable {
        private ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("enter");
                long startTime = System.currentTimeMillis();
                for (; ; ) {
                    if (System.currentTimeMillis() - startTime >= 5000) {
                        break;
                    }
                }
                System.out.println("end");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        }
    }

    @Test
    public void testLockInterruptThread() throws InterruptedException {
        //使用ReentrantLock#lockInterruptibly 在没有获取到锁处于等待过程中是可以被中断的
       // LockInterruptThread_1 lockInterruptThread = new LockInterruptThread_1();

        //使用synchronized关键字 在没有获取到锁处于等待过程中是无法随意中断的
        //LockInterruptThread_2 lockInterruptThread = new LockInterruptThread_2();

        //使用ReentrantLock#lock 在没有获取到锁处于等待过程中是无法随意中断的
        LockInterruptThread_3 lockInterruptThread = new LockInterruptThread_3();

        Thread t1 = new Thread(lockInterruptThread);
        Thread t2 = new Thread(lockInterruptThread);

        t1.start();
        t2.start();

        Thread.sleep(200);
        System.out.println("开始触发中断操作");
        t2.interrupt();
        System.out.println("发起了中断操作");

    }
}
