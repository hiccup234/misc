package com.hiccup.misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhy on 2018/2/9.
 */
public class DumpFileTest {

    public static void main(String[] args) {
        // 虚拟机参数配置
//        -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=E:\Dump\dumpFileTest.dump
        List list = new ArrayList();
        for(int i=0; i<5; i++) {
            list.add(new byte[5*1024*1024]);
        }
    }
}
