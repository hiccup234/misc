package top.hiccup.jdk.container;

import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;

import org.junit.Test;

/**
 * 1、Hashtable：早期(1.0)Java类库的哈希表实现，本身是同步的（线程安全），不支持null键和null值
 * <p>
 * 2、HashMap：不是线程安全的，支持null键和null值，存储在第一个槽位
 * <p>
 * 3、LinkedHashMap：提供遍历顺序符合插入顺序(通过双向链表实现)
 * <p>
 * 4、TreeMap：基于红黑树的有序Map(由key的自然序或者指定的Comparator决定)
 *
 * @author wenhy
 * @date 2019/1/3
 */
public class MapTest {

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    @Test
    public void test() {
        System.out.println(tableSizeFor(1));
        System.out.println(tableSizeFor(2));
    }

    @Test
    public void test2() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(null, null);

//        Hashtable<String, Object> hashtable = new Hashtable<>();
////        hashtable.put(null, null);

        Map<String, String> m = new WeakHashMap<>(3);

        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("sdf", 123);
        treeMap.put("abc", 234);
        treeMap.entrySet().forEach((entry) -> {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        });
    }


    public static void main(String[] args) {
        // 2G堆内存（-Xms2G -Xmx2G），大概能存2000W左右（23988213）
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>(1024, 1);
        while (true) {
            map.put(count, count++);
            System.out.println(count);
        }
    }
}
