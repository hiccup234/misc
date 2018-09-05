package com.hiccup.jdk.vm.jmm;

/**
 * JVM内存模型
 *
 * 内部以补码表示数据（正数的补码是本身，负数的补码是按位取反再加1）
 *
 * 【PC计数器】
 * 1.线程私有（每个线程拥有一个计数器），在线程创建的时候创建
 * 2.指向下一条指令的地址，执行本地方法(native)时，值为undefined
 * 3.JVM中唯一一个不会产生OutOfMemoryError的区域
 *
 * 【方法区（元数据区）】
 * 1.保存装载的类信息，通常也叫永久区(Perm)
 * 2.方法区是JVM的一个规范，是一个逻辑分区，不同的虚拟机的实现不一样
 *  hotspot是把方法区放到了堆的永久代中(JDK8以前)，但在JDK8以后，永久代被移除，方法区放到了本地内存中(元空间)
 *  因此JDK8中的-XX:MaxPermSize已经失效了，取而代之的是-XX:MaxMetaspaceSize参数
 * 3.JDK6时，String等常量信息置于方法区中的运行时常量池，JDK7时，已经移动到了堆中
 *
 * 【堆区】
 * 1.存放对象，所有线程共享
 * 2.堆分为：年轻代、老年代、永久代(也叫方法区，存放加载的类信息、常量、静态变量、即时编译器编译后的代码等数据，
 *  jdk1.8以前方法区在堆里面，1.8以后是单独的，在元空间)
 * 3.年轻代也叫新生代，分为1个eden区和2个servivor区(s0和s1)，新生代存放新创建的对象，满了以后，会触发Scavenge GC，回收非存活对象，
 *  同时将存活对象放入servivor区,servivor区的from和to会在gc时从from移动到to，当to满的时候，就把对象移动到老年代，
 *  同时from和to互换。因此只有多次Scavenge GC都存活的对象，才会放入老年代。Scavenge GC不会触发老年代和永久代的垃圾回收
 *  老年代满了以后，会触发full gc,会清除老年代和永久代的非存活对象
 * 4.新生代GC也叫Young GC或者Minor GC，老年代GC叫Full GC
 * 5.full gc对整个堆进行整理，包括Young、Tenured和Perm。Full GC因为需要对整个对进行回收，
 *  所以比Scavenge GC要慢，因此应该尽可能减少Full GC的次数。在对JVM调优的过程中，很大一部分工作就是对于FullGC的调节
 *  有如下原因可能导致Full GC：
 *      年老代（Tenured）被写满
 *      永久代（Perm）被写满
 *      System.gc()被显示调用
 *      上一次GC之后Heap的各域分配策略动态变化
 *
 * 【栈区】
 * 1.每个线程创建的时候创建一个栈，线程每一次调用方法时创建一个帧，然后压入线程栈中
 * 2.帧由局部变量表、操作数栈、帧数据区(如常量池指针等)组成
 * 3.Java没有数据寄存器，所有参数传递都靠”操作数栈”，有返回值的方法返回时，会把返回值放到调用者方法的操作数栈中
 *
 *
 * @author wenhy
 * @date 2018/8/17
 */
public class JvmMmTest {

    public static void main(String[] args) {
        int i = 0;
        byte[] bytes = new byte[1024*1024];
        while (true) {
            String s = new String(bytes);
            System.out.println(String.valueOf(s+i++).intern());
        }
    }
}
