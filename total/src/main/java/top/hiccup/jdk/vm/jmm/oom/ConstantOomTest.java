package top.hiccup.jdk.vm.jmm.oom;

import java.util.LinkedList;
import java.util.List;

/**
 * 常量池内存溢出测试
 * <p>
 * 【堆溢出】
 * 1.heap space，应用程序需要分配的堆内存大于-Xmx设定值时，解决方案是增加堆空间，及时释放内存（GC）
 * 2.heap space，应用程序需要分配的堆内存没有大于-Xmx设定值，但操作系统没有多余空间分配给JVM时，
 * 所以一般将-Xms与-Xmx设定成相同，一开始就把空间分配给JVM，避免扩展的时候操作系统剩余内存不够
 * <p>
 * 【栈溢出】
 * 1.unable to create new native thread，操作系统限制（如Linux默认的普通用户只能创建1024个线程）
 * 2.unable to create new native thread，操作系统没有更多内存创建线程。解决方案是减小栈空间或者减小堆空间，使其能创建更多线程
 * 3.java.lang.StackOverflowError，栈空间单个线程内存溢出，解决方案是增加栈空间，或者减少方法参数、减少递归调用深度
 * <p>
 * 【方法区溢出】
 * 1.PermGenspace（Metaspace），加载的类过多等情况
 * <p>
 * 【直接内存溢出】
 * 1.NIO中使用了直接内存
 *
 * @VM args: -XX:PermSize
 *           --XX:MaxPermSize （JDK1.8以前）
 * @VM args: -Xms10M
 *           -Xmx10M
 *           -XX:+PrintGCDetails
 *           -XX:+HeapDumpOnOutOfMemoryError
 *           -XX:HeapDumpPath=C:\Users\haiyang.wen\Desktop\HeapDump （JDK1.8常量池放在了堆上）
 *
 * @author wenhy
 * @date 2018/8/31
 */
public class ConstantOomTest {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        String s = "abc";
        long count = 0;
        while (true) {
            String temp = s + count++;
            System.out.println(temp);
            // 持有引用，避免GC
            list.add(temp.intern());
        }
    }
}
