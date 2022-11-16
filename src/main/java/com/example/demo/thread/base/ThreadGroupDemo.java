package com.example.demo.thread.base;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanzx
 * @date 2022/11/15 23:21
 */
public class ThreadGroupDemo {
    public static void main(String[] args) {
        List<Thread> dbConnThreadList = dbConnThread();
        List<Thread> httpReqThreadList = httpReqThread();
        startThread(dbConnThreadList);
        startThread(httpReqThreadList);
    }

    public static void startThread(List<Thread> threadList) {
        for (Thread thread : threadList) {
            thread.start();
        }
    }

    public static List<Thread> dbConnThread() {
        ThreadGroup dbConnThreadGroup = new ThreadGroup("数据库连接线程组");
        List<Thread> dbThreadList = Lists.newArrayList();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(dbConnThreadGroup, new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名: " + Thread.currentThread().getName()
                            + ", 所在线程组: " + Thread.currentThread().getThreadGroup().getName());
                }
            }, "db-conn-thread-" + 1);
            dbThreadList.add(thread);
        }
        return dbThreadList;
    }

    public static List<Thread> httpReqThread() {
        ThreadGroup httpReqThreadGroup = new ThreadGroup("第三方http请求线程组");
        List<Thread> httpReqThreadList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(httpReqThreadGroup, new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名: " + Thread.currentThread().getName()
                            + ", 所在线程组: " + Thread.currentThread().getThreadGroup().getName());
                }
            }, "http-req-thread-" + i);
            httpReqThreadList.add(t);
        }
        return httpReqThreadList;
    }

}
