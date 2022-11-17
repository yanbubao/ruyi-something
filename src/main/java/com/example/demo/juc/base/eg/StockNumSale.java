package com.example.demo.juc.base.eg;

import java.util.concurrent.CountDownLatch;

/**
 * 模拟扣减库存的程序
 *
 * @Author yanzx
 * @Date 2022/11/16 18:57
 */
public class StockNumSale {
    //车票剩余数目
    private int stockNum;

    public StockNumSale(int stockNum) {
        this.stockNum = stockNum;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            batchTest(200, 1000);
        }
    }

    public static void batchTest(int threadNum, int stockNum) {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(threadNum);

        StockNumSale stockNumSale = new StockNumSale(stockNum);
        for (int i = 0; i < threadNum; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //等待，模拟并发
                        begin.await();
                        stockNumSale.lockStock(100);
                        end.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }


        try {
            begin.countDown();
            end.await();
            stockNumSale.printStockNum();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 锁定库存
     *
     * @return 是否锁定成功
     */
    private boolean lockStock(int num) {
        synchronized (this) {
            if (!isStockEnough()) {
                return false;
            }
            for (int i = 0; i < num; i++) {
                stockNum--;
            }
            return true;
        }
    }

    private boolean isStockEnough() {
        return stockNum > 0;
    }

    public void printStockNum() {
        if (this.stockNum < 0) {
            System.out.println("库存不足：" + this.stockNum);
        } else {
            System.out.println("库存：" + this.stockNum);
        }
    }

}
