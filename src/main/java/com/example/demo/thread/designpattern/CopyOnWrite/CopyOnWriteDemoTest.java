package com.example.demo.thread.designpattern.CopyOnWrite;

import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWrite 模式的核心我们可以理解为：当某个人想要修改相关内容的时候，
 * 就先将真正的内容 Copy 出去形成一个新的内容，然后再去修改。当修改好了之后，再替换掉之前的内容。
 * <p>
 * 这种模式在我们的实际工作中也是经常会使用到的，例如 JDK 中的 COW 系列，
 * 下边的这组案例是关于使用 CopyOnWriteArrayList 和 CopyOnWriteArraySet 的介绍：
 * <p>
 * 通常 COW 这种思想在高并发场景中会有所使用。例如当我们在应用中设计了一份黑名单用户表，
 * 在内存中可以采用 CopyOnWriteSet 集合将用户的 id 存储起来，通常我们会在服务的网关处去核对该用户是否属于黑名单用户。
 * 另外这种场景是属于经典的读多写少的场景，而 COW 的设计效果正好符合，
 * 当需要修改黑名单数据的时候，copy 一份数据出来修改，当修改完后重新赋值即可。
 *
 * @Author yanzx
 * @Date 2022/11/17 16:59
 */
public class CopyOnWriteDemoTest {

    @Test
    public void showCopyOnWriteArrayList() {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("idea1");
        copyOnWriteArrayList.add("idea2");
        copyOnWriteArrayList.add("idea3");
        copyOnWriteArrayList.add("idea4");
        System.out.println(copyOnWriteArrayList.get(0));
    }

    @Test
    public void showCopyOnWriteArraySet() {
        CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
        copyOnWriteArraySet.add("t1");
        copyOnWriteArraySet.add("t1");
        copyOnWriteArraySet.add("t1");
        System.out.println(copyOnWriteArraySet.size());
    }

}
