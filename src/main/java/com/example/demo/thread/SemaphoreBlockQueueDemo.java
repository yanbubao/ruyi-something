package com.example.demo.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author yanzx
 * @Date 2022/11/16 23:23
 */
public class SemaphoreBlockQueueDemo {
    private final Semaphore semaphore;
    private final List<Object> list;
    private final int maxLen;

    public SemaphoreBlockQueueDemo(int size) {
        this.list = new ArrayList<>(size * 2);
        this.maxLen = size;
        this.semaphore = new Semaphore(size);
    }

    public void add(Integer item) {
        int currentIndex = 0;
        if (currentIndex == maxLen) {
            throw new IndexOutOfBoundsException("队列存储空间有限，不能再放入新的元素");
        }
        list.add(item);
        semaphore.release(1);
    }

    public Object take(long time, TimeUnit timeUnit) {
        if (list.size() == 0 || list.get(0) == null) {
            try {
                //如果没有放入新的元素，这里会阻塞
                semaphore.tryAcquire(time, timeUnit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        Object currentValue = list.get(0);
        list.remove(0);
        return currentValue;
    }


    public static void main(String[] args) {
        SemaphoreBlockQueueDemo blockQueue = new SemaphoreBlockQueueDemo(10);
        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    blockQueue.add(i);
                }
            }
        });
        writeThread.start();
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 101; i++) {
                    System.out.println(blockQueue.take(3, TimeUnit.SECONDS));
                }
            }
        });
        readThread.start();

    }
}
