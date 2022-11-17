package com.example.demo.juc.parallelStreamDemo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author yanzx
 * @Date 2022/11/17 18:55
 */
public class ParallelStreamDemoTest {

    /**
     * 对非线程安全集合使用并行流
     * <p>
     * 当我们使用 parallelStream 这类函数的时候，对于集合容器类型，建议使用 JUC 内部的组件会更加合适一些
     * <p>
     * 使用parallelStream后真的可以保证一定并行吗？
     * <p>
     * 关于这一点，其实是不一定的。因为 parallelStream的底层实际上是使用了一个ForkJoinPool线程池。
     * 该线程池专门用于执行并行调用，且在调用任务的过程中，所产生的总线程数是当前操作系统运行环境的 CPU 个数减1
     */
    @Test
    public void userNotSafeCollection() {
        List<Integer> idCOWList = new CopyOnWriteArrayList<>();
        List<Integer> idArrayList = new ArrayList<>();
        List<String> numList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            numList.add(String.valueOf(i));
        }
        numList.parallelStream().forEach(x -> idCOWList.add(Integer.valueOf(x)));
        System.out.println(idCOWList.size());

        numList.parallelStream().forEach(x -> idArrayList.add(Integer.valueOf(x)));
        System.out.println(idArrayList.size());
    }
}
