package com.example.demo.juc.base.deadlock.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * 锁的分配者
 *
 * @author yanzx
 */
public class AllocatorHolder {

    private List<Object> list = new ArrayList<>();

    synchronized boolean applyLock(Object from, Object to) {

        if (list.contains(from) || list.contains(to)) {
            return false;
        }

        list.add(from);
        list.add(to);
        return true;
    }

    synchronized void freeLock(Object from, Object to) {
        list.remove(from);
        list.remove(to);
    }
}
