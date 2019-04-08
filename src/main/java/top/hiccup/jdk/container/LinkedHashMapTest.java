package top.hiccup.jdk.container;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自动释放掉最不常访问的对象
 *
 * @author wenhy
 * @date 2019/4/8
 */
public class LinkedHashMapTest {

    public static void main(String[] args) {
        // 注意构造入参的accessOrder为true：访问序，最新访问的放到链表尾部去，默认为false：插入序
        LinkedHashMap<String, String> accessOrderedMap = new LinkedHashMap<String, String>(16, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                // 实现自定义删除策略
                return size() > 3;
            }
        };
        accessOrderedMap.put("Project1", "Valhalla");
        accessOrderedMap.put("Project2", "Panama");
        accessOrderedMap.put("Project3", "Loom");
        accessOrderedMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        // 模拟访问
        accessOrderedMap.get("Project1");
        accessOrderedMap.get("Project1");
        accessOrderedMap.get("Project3");
        System.out.println("Iterate over should be not affected:");
        accessOrderedMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        // 触发删除
        accessOrderedMap.put("Project4", "Mission Control");
        System.out.println("Oldest entry should be removed:");
        accessOrderedMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }
}


