package top.hiccup.jdk.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JDK8的ConcurrentHashMap假死锁问题，而JDK8的HashMap不会有这样的问题
 *
 * @author wenhy
 * @date 2019/4/4
 * @see https://bugs.openjdk.java.net/browse/JDK-8062841
 */
public class HashMap8InfiniteLoopTest {

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.computeIfAbsent("hiccup",
                (String key) -> {
                    // 这里新添加了key
                    map.put("test", "value");
                    return "value";
                });
    }
}
