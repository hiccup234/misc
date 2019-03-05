package top.hiccup.jdk.container;

import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;

/**
 * 1、Hashtable：早期(1.0)Java类库的哈希表实现，本身是同步的（线程安全），不支持null键和null值
 *
 * 2、HashMap：不是线程安全的，支持null键和null值，存储在第一个槽位
 *
 * 3、LinkedHashMap：提供遍历顺序符合插入顺序(通过双向链表实现)
 *
 * 4、TreeMap：基于红黑树的有序Map(由key的自然序或者指定的Comparator决定)
 *
 * =============================================================================================================
 * Q: 解决hash冲突的常用方法：
 * 1、开放定址法：当key的哈希码hash = H(key)出现冲突时，再以hash为基础产生另外一个哈希码hash2 = H(hash)，以此类推直到不再重复
 * 2、再哈希法: 同时提供多个不同的哈希函数H1，H2，H3...如果发生冲突，则hash=H2(key),hash=H3(key)...
 * 3、链地址法：思想同HashMap
 * 4、公共溢出区法：将哈希表分为基本表和溢出表，只要发生哈希冲突就直接填入溢出区
 * =============================================================================================================
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

    public static void main(String[] args) {
//        System.out.println(tableSizeFor(1));
//        System.out.println(tableSizeFor(2));
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put(null, null);
//
//        Hashtable<String, Object> hashtable = new Hashtable<>();
//        hashtable.put(null, null);


        Map<String, String> m = new WeakHashMap<>(3);

        Map treeMap = new TreeMap();
        treeMap.put("sdf", 123);
        String s;
        Integer i;
    }
}
