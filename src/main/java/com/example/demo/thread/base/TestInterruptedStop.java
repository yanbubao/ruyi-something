package com.example.demo.thread.base;

/**
 * @Author yanzx
 * @Date 2022/11/16 15:15
 */
public class TestInterruptedStop implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            //如果当前线程被中断，这里需要主动退出
            while (!Thread.currentThread().isInterrupted()) {
            }
            System.out.println("end");
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new TestInterruptedStop());
        thread.start();
        thread.interrupt();
    }
}