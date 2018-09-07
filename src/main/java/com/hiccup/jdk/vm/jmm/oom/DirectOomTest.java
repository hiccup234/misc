package com.hiccup.jdk.vm.jmm.oom;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * JVM直接内存溢出测试
 *
 * @author wenhy
 * @date 2018/9/5
 */
public class DirectOomTest {

    public static void test() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        while (true) {
            unsafe.allocateMemory(1024 * 1024);
        }
    }

    /**
     * NIO中会用到直接内存
     */
    public static void test2() {
        List<ByteBuffer> bufs = new ArrayList<ByteBuffer>();
        for(int i=0; i<1024 * 100; i++){
            bufs.add(ByteBuffer.allocateDirect(1024*1024));
        }
    }

    public static void main(String[] args) throws Throwable {
//        test();
        test2();
    }

}
