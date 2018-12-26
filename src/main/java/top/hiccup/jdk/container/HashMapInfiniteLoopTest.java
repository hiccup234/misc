package top.hiccup.jdk.container;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap假死锁问题的测试、分析和总结：https://www.cnblogs.com/ocean234/p/9063379.html
 *
 *  基于JDK1.7测试HashMap在多线程环境下假死锁的情况
 *  JDK1.8的HashMap实现跟1.7比较已经有很大的变化，已不存在这样的问题
 *  （其实这本来不是JDK的一个问题，HashMap本就不是线程安全的，多线程环境下共享一定要用线程安全的Map容器）
 *
 * @author wenhy
 * @date 2018/1/3
 */
public class HashMapInfiniteLoopTest {

    public static void main(String[] args) {
        // JDK版本
        String jdkVer = System.getProperty("java.version");
        // 32位还是64位
        String jdkMod = System.getProperty("sun.arch.data.model");
        System.out.println(jdkVer + "#" + jdkMod);

        final Map<String, String> map = new HashMap<>();
//        final Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    for (int j = 0; j < 1000; j++) {
                        map.put("" + j + "_" + System.currentTimeMillis(), "" + j + "_" + System.currentTimeMillis());
                    }
                }
            }, "myThread_" + i).start();
        }
    }

}
