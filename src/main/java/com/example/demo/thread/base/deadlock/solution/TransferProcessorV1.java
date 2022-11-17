package com.example.demo.thread.base.deadlock.solution;

import april.concurrent.deadlock.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通过AllocatorHolder进行分配锁解决死锁问题
 *
 * @author yanzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferProcessorV1 implements Runnable {

    private Account fromAccount;

    private Account toAccount;

    private int amount;

    private AllocatorHolder allocatorHolder;


    @Override
    public void run() {
        // 模拟一直循环转账的操作
        while (true) {
            // 锁持有者获取锁
            if (allocatorHolder.applyLock(fromAccount, toAccount)) {

                try {
                    synchronized (fromAccount) {

                        synchronized (toAccount) {

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

                } finally {
                    // 锁持有者释放锁
                    allocatorHolder.freeLock(fromAccount, toAccount);
                }
            }

        }
    }


    public static void main(String[] args) {
        Account fromAccount = new Account("yanzx", 100000);
        Account toAccount = new Account("James", 300000);

        // lock holder
        AllocatorHolder allocatorHolder = new AllocatorHolder();

        Thread t1 = new Thread(new TransferProcessorV1(fromAccount, toAccount, 10, allocatorHolder), "t1");
        Thread t2 = new Thread(new TransferProcessorV1(toAccount, fromAccount, 30, allocatorHolder), "t2");

        t1.start();
        t2.start();
    }
}
