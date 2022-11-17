package com.example.demo.juc.designpattern.GuardedSuspension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 这段代码中我模拟实现了一个 MQ 的发送类，在这个 MQ 的发送类中有一个 sendMsg 方法，
 * 它主要负责将消息发送到 broker 方。但是在获取到 broker 返回的确认信号之后，再将是否投递成功结果返回给主线程，
 * 代码中加入了一个 CountDownLatch 类用于阻塞，当 broker 返回确认信号之后再放开这个 CountDownLatch。
 * <p>
 * Guarded Suspension 模式的本质，其实可以理解为是多线程中的一种等待唤醒机制实现的标准，
 * 在我们实际工作中所使用的很多中间件都有用到过这种模式，例如 RocketMQ 底层在发送了消息之后，
 * 就会处于一个等待状态，当 message 被写入到 broker 之后，broker 回传一个信号给到 provider 节点，
 * 最后放开对应的 CountDownLatch 并且告知发送方消息发送成功
 *
 * @Author yanzx
 * @Date 2022/11/17 16:13
 */
public class MQProducer {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        final MQProducer mqProducer = new MQProducer();
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mqProducer.receiveBrokerMsg("broker receive msg...");
        });

        t.start();

        SendResult sendResult = mqProducer.sendMsg("test", "test-topic");
        System.out.println(sendResult);
    }

    /**
     * 发送消息给到broker节点
     *
     * @param content
     * @param topic
     * @return SendResult
     */
    public SendResult sendMsg(String content, String topic) {
        this.sendMsgToBroker(content, topic);

        try {
            boolean countDownStatus = countDownLatch.await(3, TimeUnit.SECONDS);
            if (countDownStatus) {
                return SendResult.SUCCESS;
            }
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        return SendResult.FAIL;
    }

    /**
     * 当broker返回消息成功写入后回调这里
     */
    private void receiveBrokerMsg(String msg) {
        //处理broker返回的心跳包
        countDownLatch.countDown();
        System.out.println("countDown");
    }

    /**
     * 模拟发送消息到broker节点
     *
     * @param content
     * @param topic
     */
    private void sendMsgToBroker(String content, String topic) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    enum SendResult {
        SUCCESS, FAIL
    }
}
