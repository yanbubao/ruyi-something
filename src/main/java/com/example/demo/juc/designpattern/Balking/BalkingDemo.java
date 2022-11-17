package com.example.demo.juc.designpattern.Balking;

/**
 * 这段代码中模拟了一个文件保存的案例，当 isChange 变量被外界条件所修改了的话，
 * 调用 saveFile 函数就会执行文件保存的动作。在代码案例中，我们模拟了多个线程执行修改和保存的动作，
 * 在这种多线程交互的场景中，对于 if 判断的处理尤其关键。
 * <p>
 * 在代码中我们可以看到，对于 isChange 变量的判断，我们都是使用了 synchronized 关键字去访问，
 * 这样可以保证每次只会有一个线程去访问到 isChange 变量，从而避免了线程安全问题
 *
 * @Author yanzx
 * @Date 2022/11/17 16:50
 */
public class BalkingDemo {
    private boolean isChange = false;

    public void saveFile() {
        synchronized (this) {
            if (isChange) {
                //将缓冲区的代码写入到磁盘中
                System.out.println("保存磁盘");
                isChange = false;
            }
        }
    }

    public void changeFile() {
        synchronized (this) {
            isChange = true;
            //修改缓冲区的数据
            System.out.println("修改缓冲数据");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BalkingDemo balkingDemo = new BalkingDemo();
        for (int i = 0; i < 10; i++) {
            Thread saveThread = new Thread(balkingDemo::saveFile);
            Thread changeThread = new Thread(balkingDemo::changeFile);
            saveThread.start();
            changeThread.start();
            Thread.sleep(1000);
        }

    }
}
