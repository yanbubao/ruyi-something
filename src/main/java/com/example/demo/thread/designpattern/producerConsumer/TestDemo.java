package com.example.demo.thread.designpattern.producerConsumer;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author yanzx
 * @Date 2022/11/17 16:08
 */
public class TestDemo {

    private static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(20);

    public static void main(String[] args) throws InterruptedException {
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        consumer.start();

        while (true) {
            System.out.println("投递元素");
            producer.put(UUID.randomUUID().toString());
            Thread.sleep(1000);
        }
    }
}
