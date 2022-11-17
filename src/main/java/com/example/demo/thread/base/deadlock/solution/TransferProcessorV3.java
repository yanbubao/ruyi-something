package com.example.demo.thread.base.deadlock.solution;

import april.concurrent.deadlock.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 手动设置加锁顺序解决死锁问题，业务场景中不适用
 *
 * @author yanzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferProcessorV3 implements Runnable {

    private Account fromAccount;

    private Account toAccount;

    private int amount;

    @Override
    public void run() {

        Account left = null;
        Account right = null;
        if (fromAccount.hashCode() > toAccount.hashCode()) {
            left = toAccount;
            right = fromAccount;
        }

        // 模拟一直循环转账的操作
        while (true) {
            synchronized (left) {
                synchronized (right) {
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


        Thread t1 = new Thread(new TransferProcessorV3(fromAccount, toAccount, 10), "t1");
        Thread t2 = new Thread(new TransferProcessorV3(toAccount, fromAccount, 30), "t2");

        t1.start();
        t2.start();
    }
}
