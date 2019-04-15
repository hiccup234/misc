package top.hiccup.jdk.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 1、ThreadLocal可用于解决线程安全问题，创建线程隔离的变量，是一种无锁解决线程安全问题的方式。
 *   但是本身并不存在线程安全与不安全的说法，因为是线程私有的，不存在多线程访问
 *
 * @author wenhy
 * @date 2019/3/19
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        ThreadLocal<List<String>> threadLocal = new ThreadLocal<>();
        threadLocal.set(list);
        while (true) {
            list.add("test"+new Random().nextInt(50000));
        }
    }
}
