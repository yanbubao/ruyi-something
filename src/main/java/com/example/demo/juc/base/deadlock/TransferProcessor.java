package com.example.demo.juc.base.deadlock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yanzx
 * @Date 2022/11/17 15:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferProcessor implements Runnable {

    private Account fromAccount;

    private Account toAccount;

    private int amount;

    @Override
    public void run() {
        // 模拟一直循环转账的操作
        while (true) {

            synchronized (fromAccount) {

                synchronized (toAccount) {

                    // 业务逻辑
                    if (fromAccount.getBalance() > this.amount) {
                        fromAccount.debit(this.amount);
                        toAccount.credit(this.amount);
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

        Thread t1 = new Thread(new TransferProcessor(fromAccount, toAccount, 10), "t1");
        Thread t2 = new Thread(new TransferProcessor(toAccount, fromAccount, 30), "t2");

        t1.start();
        t2.start();
    }
}
