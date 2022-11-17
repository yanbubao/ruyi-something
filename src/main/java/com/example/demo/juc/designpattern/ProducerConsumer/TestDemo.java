package com.example.demo.juc.designpattern.ProducerConsumer;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 生产者往一个缓冲区中投递元素，接着消费者从这个缓冲区中去提取元素，这方面的具体代表如 JDK 中的
 * java.util.concurrent.BlockingQueue 类，这个类虽然实现了 java.util.Queue 接口，
 * 也都提供了 offer 和 poll 方法，但是要想利用它的阻塞效果，还是得使用它的 put 和 take 方法。
 * <p>
 * 另外在它的底层实现方面可以选择的种类有很多种，诸如 ArrayBlockingQueue、LinkedBlockingQueue、
 * PriorityBlockingQueue、SynchronousQueue 、DelayQueue、ConcurrentLinkedQueue。
 *
 * @Author yanzx
 * @Date 2022/11/17 16:08
 */
public class TestDemo {

    private static final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(20);

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
