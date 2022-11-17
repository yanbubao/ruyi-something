package com.example.demo.juc.base;

/**
 * <p>
 *     “优先级”这个参数通常并不是那么地“靠谱”，理论上说线程的优先级越高，分配到时间片的几率也就越高，
 *     但是在实际运行过程中却并非如此，优先级只能作为一个参考数值，而且具体的线程优先级还和操作系统有关，
 *     所以大家在编码中如果使用到了“优先级”的设置，请不要强依赖于它
 * </p>
 *
 * @author yanzx
 * @date 2022/11/15 23:44
 */
public class ThreadPriorityDemo {
    static class InnerTask implements Runnable {
        private int i;

        public InnerTask(int i) {
            this.i = i;
        }

        public void run() {
            for (int j = 0; j < 10; j++) {
                System.out.println("ThreadName is " + Thread.currentThread().getName() + " " + j);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new InnerTask(10), "task-1");
        t1.setPriority(1);
        Thread t2 = new Thread(new InnerTask(2), "task-2");
        // 优先级只能作为一个参考数值，而且具体的线程优先级还和操作系统有关
        t2.setPriority(2);
        Thread t3 = new Thread(new InnerTask(3), "task-3");
        t3.setPriority(3);

        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(2000);
    }
}
