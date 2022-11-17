package com.example.demo.thread.designpattern.producerConsumer;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author yanzx
 * @Date 2022/11/17 16:06
 */
public class Consumer {
    private ArrayBlockingQueue<String> queue;

    public Consumer(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void start() {
        Thread consumerThread = new Thread(() -> {
            while (true) {
                try {
                    String content = queue.take();
                    System.out.println("消费数据：" + content);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        consumerThread.start();
    }
}
