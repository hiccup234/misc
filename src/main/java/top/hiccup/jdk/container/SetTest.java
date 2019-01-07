package top.hiccup.jdk.container;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * HashSet：基于HashMap
 *
 * LinkedHashSet：保证元素的添加顺序
 *
 * TreeSet：保证元素的自然顺序
 *
 * @author wenhy
 * @date 2019/1/7
 */
public class SetTest {

    public static void main(String[] args) {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("S1");
        hashSet.add("S2");
        hashSet.add("S3");
        hashSet.add("S4");
        hashSet.add("S5");
        hashSet.forEach(e -> System.out.print(e + " "));
        System.out.println();
        System.out.println();

        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("S3");
        linkedHashSet.add("S2");
        linkedHashSet.add("S1");
        linkedHashSet.add("S4");
        linkedHashSet.add("S5");
        linkedHashSet.forEach(e -> System.out.print(e + " "));
        System.out.println();
        System.out.println();

        Set<String> treeSet = new TreeSet<>();
        treeSet.add("S5");
        treeSet.add("S2");
        treeSet.add("S1");
        treeSet.add("S4");
        treeSet.add("S3");
        treeSet.forEach(e -> System.out.print(e + " "));
    }
}
