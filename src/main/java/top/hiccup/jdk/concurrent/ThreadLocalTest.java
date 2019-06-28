package top.hiccup.jdk.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 1、ThreadLocal可用于解决线程安全问题，创建线程隔离的变量，是一种无锁解决线程安全问题的方式。
 * 但是本身并不存在线程安全与不安全的说法，因为是线程私有的，不存在多线程访问，即不存在竞争条件。
 * 
 * 2、使用完之后一定要记得remove，不然在线程池中很容易造成内存泄漏
 * （线上亲历过：日志追踪框架中用了ThreadLocal来set跟request相关的对象但请求返回后未remove造成内存泄漏服务宕机）
 * 
 * 3、如果看源码就可以了解到，其实每个线程都持有实例属性：
 * ThreadLocal.ThreadLocalMap threadLocals = null;
 * 所以set和get方法都是先取当前线程Thread.currentThread();然后取得ThreadLocalMap再set和get。
 *
 * @author wenhy
 * @date 2019/3/19
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        ThreadLocal<List<String>> threadLocal = new ThreadLocal<>();
        threadLocal.set(list);
        
        int i = 0;
        while (i < 1) {
            list.add("test" + new Random().nextInt(50000));
            System.out.println(threadLocal.get());
        }
        // 一般情况下，使用完之后一定要记得remove（因为一般Web项目都会使用线程池，所以线程可能会生存很长一段时间）
        threadLocal.remove();
    }
}
