package com.example.demo.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author yanzx
 * @Date 2022/11/16 22:52
 */
public class PrintAbcDemo_2 {
    private int signal = 0;
    Lock lock = new ReentrantLock();
    Condition a = lock.newCondition();
    Condition b = lock.newCondition();
    Condition c = lock.newCondition();

    public void printA() {
        lock.lock();
        while (this.signal != 0) {
            try {
                a.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        signal++;
        System.out.println("a");
        b.signal();
        lock.unlock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
    }

    public void printB() {
        lock.lock();
        while (signal != 1) {
            try {
                b.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        signal++;
        System.out.println("b");
        c.signal();
        lock.unlock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void printC() {
        lock.lock();
        while (signal != 2) {
            try {
                c.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        signal = 0;
        System.out.println("c");
        a.signal();
        lock.unlock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PrintAbcDemo_2 printAbcDemo_2 = new PrintAbcDemo_2();
        Thread printAThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    printAbcDemo_2.printA();
                }
            }
        });
        printAThread.start();

        Thread printBThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    printAbcDemo_2.printB();
                }
            }
        });
        printBThread.start();

        Thread printCThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    printAbcDemo_2.printC();
                }
            }
        });
        printCThread.start();
    }
}
