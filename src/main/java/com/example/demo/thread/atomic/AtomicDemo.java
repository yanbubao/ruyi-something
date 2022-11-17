package com.example.demo.thread.atomic;

import lombok.Data;
import org.junit.Test;

import java.util.concurrent.atomic.*;

/**
 * @Author yanzx
 * @Date 2022/11/17 14:22
 */
public class AtomicDemo {

    /**
     * 原子更新基本类型
     */
    @Test
    public void testAtomicInteger() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        System.out.println(atomicInteger.addAndGet(1));
        System.out.println(atomicInteger.getAndAdd(1));
        System.out.println(atomicInteger.get());
    }

    @Test
    public void testAtomicBoolean() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        atomicBoolean.set(false);
        System.out.println(atomicBoolean.get());
    }

    /**
     * 原子更新数组
     */
    @Test
    public void testAtomicIntegerArray() {
        int[] value = new int[]{0, 1, 2};
        AtomicIntegerArray ata = new AtomicIntegerArray(value);
        //先指定数组的下标位，在指定需要增加的数值
        ata.addAndGet(0, 1);
        System.out.println(ata);
    }

    /**
     * 原子更新引用对象
     */
    @Test
    public void testAtomicReference() {
        AtomicReference<String> stringAtomicReference = new AtomicReference<>("idea");
        System.out.println(stringAtomicReference.compareAndSet("idea", "idea2"));
        System.out.println(stringAtomicReference.compareAndSet("idea2", "idea"));
        System.out.println(stringAtomicReference.compareAndSet("idea", "idea2"));
    }

    /**
     * 更新整个对象的类型
     */
    @Test
    public void updateAccount() {
        AtomicReference<Account> accountAtomicReference = new AtomicReference<>();
        Account account = new Account();
        account.setAge(1);
        account.setId(1);
        account.setName("idea");
        accountAtomicReference.set(account);
        Account account2 = new Account();
        account2.setAge(2);
        account2.setId(2);
        account2.setName("idea2");
        accountAtomicReference.compareAndSet(account, account2);
        System.out.println(accountAtomicReference.get().getAge());
        System.out.println(accountAtomicReference.get().getId());
        System.out.println(accountAtomicReference.get().getName());
    }

    /**
     * 原子更新引用对象字段
     * <p>
     * 更新指定对象的某个字段
     */
    @Test
    public void updateAccountField() {
        Account account = new Account();
        account.setAge(1);
        account.setId(1);
        account.setName("idea");
        //age字段一定要为volatile类型
        AtomicIntegerFieldUpdater<Account> accountAtomicReference =
                AtomicIntegerFieldUpdater.newUpdater(Account.class, "age");

        Account account2 = new Account();
        account2.setAge(2);
        account2.setId(2);
        account2.setName("idea2");
        //这里输出的数值中，age会加2
        System.out.println(accountAtomicReference.incrementAndGet(account2));
    }


    @Data
    static class Account {
        public volatile int age;
        private int id;
        private String name;
    }

}
