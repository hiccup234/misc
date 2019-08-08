package top.hiccup.jdk.concurrent.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder实现思路类似于JDK7及以前的ConcurrentHashMap，是对锁粒度的一个细化。
 *
 * LongAdder有一个根据当前并发状况动态改变的Cell数组，Cell对象里面有一个long类型的value用来存储值;
 * 开始没有并发争用的时候或者是cells数组正在初始化的时候，会使用cas来将值累加到成员变量的base上，在并发争用的情况下，LongAdder会初始化cells数组，
 * 在Cell数组中选定一个Cell加锁，数组有多少个cell，就允许同时有多少线程进行修改，最后将数组中每个Cell中的value相加，在加上base的值，就是最终的值；
 * cell数组还能根据当前线程争用情况进行扩容，初始长度为2，每次扩容会增长一倍，直到扩容到大于等于cpu数量就不再扩容，
 * 这也就是为什么LongAdder比cas和AtomicInteger效率要高的原因，后面两者都是volatile+cas实现的，他们的竞争维度是1，
 * LongAdder的竞争维度为“Cell个数+1”为什么要+1？因为它还有一个base，如果竞争不到锁还会尝试将数值加到base上；
 *
 * 【线程同步性能对比】
 * 1. 单线程下synchronized效率最高（偏向锁或者轻量级锁）；
 * 2. AtomicInteger效率最不稳定，不同并发情况下表现不一样：短时间低并发下，效率比synchronized高，
 * 有时甚至比LongAdder还高出一点，但是高并发下，性能还不如synchronized，不同情况下性能表现很不稳定；
 * 3. LongAdder性能稳定，在各种并发情况下表现都不错，整体表现最好，短时间的低并发下比AtomicInteger性能差一点，长时间高并发下性能最高
 *
 * LongAccumulator 与 LongAdder 对别，后者是前者的特殊实现（内置了累加规则，即加法），而前者是可以指定初始值和累加规则的
 *
 * @author wenhy
 * @date 2019/4/9
 */
public class LongAdderTest {

    public static void main(String[] args) throws InterruptedException {
        LongAdder adder = new LongAdder();
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(()-> {
            for (int i = 0; i < 5000; i++) {
                adder.increment();
            }
            latch.countDown();
        }, "t1").start();

        new Thread(()-> {
            for (int i = 0; i < 5000; i++) {
                adder.increment();
            }
            latch.countDown();
        }, "t2").start();

        latch.await();
        System.out.println(adder.sum());
    }
}
