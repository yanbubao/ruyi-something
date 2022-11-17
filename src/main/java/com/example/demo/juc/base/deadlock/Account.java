package com.example.demo.juc.base.deadlock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 账户实体
 *
 * @author yanzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
    private String accountName;
    private int balance;

    public void debit(int amount) {
       /* if (this.balance - amount > 0) {
            this.balance -= amount;
        }
        throw new RuntimeException("debit error! dont debit!");*/
        this.balance -= amount;
    }

    public void credit(int amount) {
        this.balance += amount;
    }
}
