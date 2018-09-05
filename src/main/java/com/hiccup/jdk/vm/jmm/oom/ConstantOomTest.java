package com.hiccup.jdk.vm.jmm.oom;

import java.util.LinkedList;
import java.util.List;

/**
 * 常量池内存溢出测试
 *
 * @VM args: -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M -XX:+PrintGCDetails （JDK1.8）
 *
 * @author wenhy
 * @date 2018/8/31
 */
public class ConstantOomTest {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        String s = "abc";
        long count = 0;
        while(true) {
            String temp = s + count++;
            // 持有引用，避免GC
            list.add(temp.intern());
        }
    }
}
