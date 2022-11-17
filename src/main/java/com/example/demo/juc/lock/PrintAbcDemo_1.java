package com.example.demo.juc.lock;

/**
 * 下边是一个实战案例，利用多线程的思路去实现交替打印 ABC 三个字符的效果。
 *
 * @Author yanzx
 * @Date 2022/11/16 20:56
 */
public class PrintAbcDemo_1 {

    private int signal = 0;

    public synchronized void printA() {
        while (signal != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("a");
        signal = 1;
        // notice notifyAll的底层是会将所有处于等待状态的且属于同一个monitor监管的线程统统都唤醒，所以被唤醒的线程们后续又要参与一次条件竞争
        notifyAll();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void printB() {
        while (signal != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("b");
        signal = 2;
        notifyAll();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void printC() {
        while (signal != 2) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("c");
        signal = 0;
        notifyAll();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PrintAbcDemo_1 printAbcDemo_1 = new PrintAbcDemo_1();
        Thread printAThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    printAbcDemo_1.printA();
                }
            }
        });
        Thread printBThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    printAbcDemo_1.printB();
                }
            }
        });
        Thread printCThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    printAbcDemo_1.printC();
                }
            }
        });
        printAThread.start();
        printBThread.start();
        printCThread.start();
        Thread.yield();
    }
}
