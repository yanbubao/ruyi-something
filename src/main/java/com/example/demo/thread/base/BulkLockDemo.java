package com.example.demo.thread.base;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量撤销偏向锁demo.
 * <p>
 * 假设有80个A类的对象实例，开始时全部偏向线程t1，然后t2线程对第1-40个对象又进行了加锁处理，此时根据批量重定向机制，1-19个对象先是会膨胀为轻量级锁，
 * 退出同步块后变为无锁；而第20-40个对象会因为触发批量重定向，锁状态变为偏向t2线程的偏向锁。这时t3线程来了，它对第21-43个对象进行加锁处理
 * （注意t3线程的前20个对象不能跟t2线程的前20个对象重合），这时由于t2线程撤销偏向锁撤销了19次（JVM会按20次计算），t3线程撤销偏向锁撤销了19次
 * （JVM会按20次计算），总共撤销的次数达到了40的阈值，此时JVM会判定为这个A类的对象有问题（不断的切换偏向线程会降低执行效率），从第21-43，
 * 都会变为轻量级锁，不再进行重偏向操作，而且会对这个A类的对象关闭偏向的设置，即往后再newA类的对象时，不会进入偏向锁状态，
 * 只能走无锁-轻量级锁0重量级锁的膨胀过程。
 *
 * @author yanzx
 */
public class BulkLockDemo {
    public static void main(String[] args) throws InterruptedException {
        List<UserLock> userList = new ArrayList<>();
        // 先睡眠5秒，保证开启偏向锁 -XX:-UseBiasedLocking -XX:BiasedLockingStartupDelay=0
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 80; i++) {
            userList.add(new UserLock()); // 开启了偏向锁，所以新建的对象都是初始化的偏向锁状态
        }
        System.out.println("userList.get(0):");
        System.out.println(ClassLayout.parseInstance(userList.get(0)).toPrintable());

        Thread t1 = new Thread(() -> {
            // 循环加锁
            for (UserLock lock : userList) {
                synchronized (lock) {
                }

            }
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        // 保证t1中的for循环结束
        Thread.sleep(3000);
        System.out.println("after t1");
        // 此时打印出来的对象头，全都是偏向t1线程的偏向锁
        System.out.println(ClassLayout.parseInstance(userList.get(0)).toPrintable());

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                // 在t2线程中循环加锁，加到第20个user的时候，会触发批量重偏向，第20个以及后面的对象变为偏向t2线程的偏向锁
                // 而之前的19个对象仍然为轻量级锁
                synchronized (userList.get(i)) {
                    if (i == 18) {
                        System.out.println("running t2: " + (i + 1));
                        System.out.println(ClassLayout.parseInstance(userList.get(i)).toPrintable());
                    }
                }
            }
        });
        t2.start();
        Thread.sleep(3000);
        System.out.println("after t2");
        System.out.println(ClassLayout.parseInstance(new UserLock()).toPrintable());

        Thread t3 = new Thread(() -> {
            for (int i = 20; i < 43; i++) {
                // 在t3线程中循环加锁，加到第20个user的时候，会触发批量重偏向，第20个以及后面的对象变为偏向t2线程的偏向锁
                // 而之前的19个对象仍然为轻量级锁
                synchronized (userList.get(i)) {
                    if (i == 30 || i == 42) {
                        System.out.println("running t3: " + (i + 1));
                        System.out.println(ClassLayout.parseInstance(userList.get(i)).toPrintable());
                    }
                }
            }
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t3.start();
        Thread.sleep(3000);
        System.out.println("after t3");
        System.out.println(ClassLayout.parseInstance(new UserLock()).toPrintable());
    }
}

/**
 * lock
 */
class UserLock {
}
