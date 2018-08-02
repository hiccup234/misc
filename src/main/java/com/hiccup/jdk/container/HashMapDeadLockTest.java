package com.hiccup.jdk.container;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenhy on 2018/5/20.
 */
public class HashMapDeadLockTest {
    /**
     * 基于JDK1.8
     */
    public static void main(String[] args) throws InterruptedException {
        Map map = new HashMap();
        map.put("fff", "aaa");


        Thread.sleep(50000);

    }

}
