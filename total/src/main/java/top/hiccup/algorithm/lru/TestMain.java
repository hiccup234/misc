package top.hiccup.algorithm.lru;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestMain {

    @Test
    public void fifo() {
        System.out.println("===========================FIFO LinkedHashMap默认实现===========================");
        final int cacheSize = 5;
        LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                return size() > cacheSize;
            }
        };
        map.put(1, "11");
        map.put(2, "11");
        map.put(3, "11");
        map.put(4, "11");
        map.put(5, "11");
        System.out.println(map.toString());
        map.put(6, "66");
        map.get(2);
        map.put(7, "77");
        map.get(4);
        System.out.println(map.toString());
    }

    @Test
    public <T> void lruCache() {
        System.out.println("===========================LRU LinkedHashMap(继承)实现===========================");
        LRUCache<Integer, String> lru = new LRUCache(5);
        lru.put(1, "11");
        lru.put(2, "11");
        lru.put(3, "11");
        lru.put(4, "11");
        lru.put(5, "11");
        System.out.println(lru.toString());
        lru.put(6, "66");
        lru.get(2);
        lru.put(7, "77");
        lru.get(4);
        System.out.println(lru.toString());
    }

    static void lruCache2() {
        System.out.println("===========================LRU LinkedHashMap delegation（代理）实现===========================");
        LRUCache2<Integer, String> lru = new LRUCache2(5);
        lru.put(1, "11");
        lru.put(2, "11");
        lru.put(3, "11");
        lru.put(4, "11");
        lru.put(5, "11");
        System.out.println(lru.toString());
        lru.put(6, "66");
        lru.get(3);
        lru.put(8, "88");
        lru.get(5);
        System.out.println(lru.toString());
    }

}
