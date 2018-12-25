package top.hiccup.jdk.lang.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * Java 4种引用类型：
 *
 * 【强引用】
 *   所谓强引用（“Strong” Reference），就是我们最常见的普通对象引用，只要还有强引用指向一个对象，就能表明对象还“活着”，垃圾收集器不会碰这种对象。
 *   对于一个普通的对象，如果没有其他的引用关系，只要超过了引用的作用域或者显式地将相应（强）引用赋值为 null，就是可以被垃圾收集的了。
 *    ↓↓
 * 【软引用】
 *   软引用（SoftReference），是一种相对强引用弱化一些的引用，可以让对象豁免一些垃圾收集，只有当 JVM 认为内存不足时，才会去试图回收软引用指向的对象。
 *   JVM 会确保在抛出 OutOfMemoryError 之前，清理软引用指向的对象。软引用通常用来实现内存敏感的缓存，
 *   如果还有空闲内存，就可以暂时保留缓存，当内存不足时清理掉，这样就保证了使用缓存的同时，不会耗尽内存。
 *    ↓↓
 * 【弱引用】
 *   弱引用（WeakReference）并不能使对象豁免垃圾收集，GC线程扫描到弱引用对象时会直接回收，仅仅是提供一种访问在弱可达状态下对象的途径。
 *   这就可以用来构建一种没有特定约束的关系，比如，维护一种非强制性的映射关系，
 *   如果试图获取时对象还在，就使用它，否则重新实例化。它同样是很多缓存实现的选择。
 *    ↓↓
 * 【幻象引用】
 *   也翻译成“虚引用”，不能通过它访问对象的任何属性或方法(get返回null)，幻象引用仅仅是提供了一种确保对象被 finalize 以后，做某些事情的机制，
 *   比如，通常用来做所谓的 Post-Mortem 清理机制，Java 平台自身 Cleaner 机制等，也有利用幻象引用监控对象的创建和销毁。
 *
 *
 *  充分理解这些引用，对于我们设计可靠的缓存等框架，或者诊断应用 OOM 等问题，会很有帮助。
 *  比如，诊断 MySQL connector-j 驱动在特定模式下（useCompression=true）的内存泄漏问题，就需要我们理解怎么排查幻象引用的堆积问题。
 *
 * ====================================================================================================================
 *  强引用就像大老婆，关系很稳固。
 *  软引用就像二老婆，随时有失宠的可能，但也有扶正的可能。
 *  弱引用就像情人，关系不稳定，可能跟别人跑了。
 *  幻像引用就是梦中情人，只在梦里出现过。
 *
 *  软引用：图片缓存框架中，“内存缓存”中的图片是以这种引用来保存，使得JVM在发生OOM之前，可以回收这部分缓存。
 *  虚引用：在静态内部类中，经常会使用虚引用。例如，一个类发送网络请求，承担callback的静态内部类，则常以虚引用的方式来保存外部类(宿主类)的引用，
 *        当外部类需要被JVM回收时，不会因为网络请求没有及时回来，导致外部类不能被回收，引起内存泄漏。
 * ====================================================================================================================
 *
 * @VM args: -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintReferenceGC
 *
 * @author wenhy
 * @date 2018/12/25
 */
public class ReferenceTypeTest {

    /**
     * 软引用通常会在最后一次引用后，还能保持一段时间，默认值是根据堆剩余空间计算的（以 M bytes 为单位）。
     * 从 Java  1.3.1 开始，提供了 -XX:SoftRefLRUPolicyMSPerMB 参数，我们可以以毫秒（milliseconds）为单位设置。
     * 比如，下面这个示例就是设置为 3 秒（3000 毫秒）。
     * @VM args: -XX:SoftRefLRUPolicyMSPerMB=3000
     */
    private static void softReferenceTest() {

    }

    private static void phantomReferenceTest() {
        Object counter = new Object();
        // 虚引用必须和引用队列 （ReferenceQueue）联合使用。
        // 当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。
        // 程序可以通过判断引用队列中是否已经加入了此虚引用，来了解被引用的对象是否将要被垃圾回收。
        // 如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取一些行动。
        ReferenceQueue refQueue = new ReferenceQueue<>();
        // 申明一个幻象引用
        PhantomReference<Object> p = new PhantomReference<>(counter, refQueue);
        // 帮助GC
        counter = null;
        System.out.println(refQueue.poll());
        // GC后，counter的finalize方法会被执行，此时counter对象已经处于幻象可达（只有幻象引用p指向它）
        System.gc();
        try {
            // 幻象引用的get方法永远返回null
            System.out.println("P-->"+p.get());
            // Remove 是一个阻塞方法，可以指定 timeout，或者选择一直阻塞
            Reference<Object> ref = refQueue.remove(1000L);
            if (ref != null) {
                System.out.println(p);
                System.out.println(ref);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        softReferenceTest();
        phantomReferenceTest();
    }
}
