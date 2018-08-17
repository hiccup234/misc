package com.hiccup.jdk.vm.jmm;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆区内存溢出测试
 *
 * @VM args ：-Xms64m -Xmx64m -XX:+PrintGCDetails   （JDK1.8）
 *
 * @author wenhy
 * @date 2018/8/17
 */
public class HeapAreaOomTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(5_000_000);
        while (true) {
            list.add(new String("测试测试"));
        }
    }

}
