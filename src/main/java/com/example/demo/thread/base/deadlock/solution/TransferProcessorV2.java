package com.example.demo.thread.base.deadlock.solution;

import april.concurrent.deadlock.Account;
import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过java.util.concurrent.locks.ReentrantLock 解决死锁问题
 *
 * @author yanzx
 */
@Data
public class TransferProcessorV2 implements Runnable {

    private Account fromAccount;

    private Account toAccount;

    private int amount;

    private Lock fromLock = new ReentrantLock();

    private Lock toLock = new ReentrantLock();

    public TransferProcessorV2(Account fromAccount, Account toAccount, int amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Override
    public void run() {
        // 模拟一直循环转账的操作
        while (true) {

            if (fromLock.tryLock()) {

                if (toLock.tryLock()) {

                    // 业务逻辑
                    if (fromAccount.getBalance() > this.amount) {
                        fromAccount.debit(this.amount);
                        toAccount.credit(this.amount);
                    } else {
                        System.exit(0);
                    }
                }
            }

            //转出账户的余额
            System.out.println(fromAccount.getAccountName() + "->" + fromAccount.getBalance());
            //转入账户的余额
            System.out.println(toAccount.getAccountName() + "->" + toAccount.getBalance());
        }

    }


    public static void main(String[] args) {
        Account fromAccount = new Account("yanzx", 100000);
        Account toAccount = new Account("James", 300000);


        Thread t1 = new Thread(new TransferProcessorV2(fromAccount, toAccount, 10), "t1");
        Thread t2 = new Thread(new TransferProcessorV2(toAccount, fromAccount, 30), "t2");

        t1.start();
        t2.start();
    }
}
