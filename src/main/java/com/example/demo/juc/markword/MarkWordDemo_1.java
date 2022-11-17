package com.example.demo.juc.markword;


import org.openjdk.jol.info.ClassLayout;

/**
 * @Author yanzx
 * @Date 2022/11/16 19:34
 */
public class MarkWordDemo_1 {
    public static void main(String[] args) {
        Object o = new Object();
        //查看该对象的内存布局
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
