package com.example.demo.juc.designpattern.ProducerConsumer;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author yanzx
 * @Date 2022/11/17 16:04
 */
public class Producer {
    private final ArrayBlockingQueue<String> queue;

    public Producer(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    public boolean put(String content) {
        try {
            queue.put(content);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void take() {
        queue.poll();
    }
}
