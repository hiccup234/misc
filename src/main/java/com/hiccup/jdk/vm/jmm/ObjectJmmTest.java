package com.hiccup.jdk.vm.jmm;

/**
 * 对象内存布局
 * 【压缩指针】 -XX:+UseCompressedOops
 * JVM中每个对象都有一个 对象头 = 标记字段(对象信息：哈希码、GC信息、锁信息) + 类型指针(指向该对象的类的对象：Class的对象)
 * 在64位JVM中标记字段占64位，类型指针也占64位，共16个字节
 * 压缩指针是指将对象指针长度压缩为32位，32位压缩指针可以寻址2的35次方个字节，对压缩指针解引用的时候需要将其左移3位再加一个固定偏移量便可以得到伪64位指针
 *
 * 哪些信息会被压缩？
 * 1.对象的全局静态变量(即类属性)
 * 2.对象头信息:64位平台下，原生对象头大小为16字节，压缩后为12字节
 * 3.对象的引用类型:64位平台下，引用类型本身大小为8字节，压缩后为4字节
 * 4.对象数组类型:64位平台下，数组类型本身大小为24字节，压缩后16字节
 *
 * 哪些信息不会被压缩？
 * 1.指向非Heap的对象指针
 * 2.局部变量、传参、返回值、NULL指针
 *
 * 【内存对齐】
 * 默认情况下，JVM堆中的对象起始地址需要对齐至8的倍数(一个字节)，如果不足则用空白填充
 *
 *
 * 【字段重排序】
 *
 * @author wenhy
 * @date 2018/8/23
 */
public class ObjectJmmTest {

    /**
     * 默认构造器字节码
     * 0 aload0 [this]
     * 1 invokespecial java.lang.Object()
     * 4 return
     */
    class Foo { }



}
