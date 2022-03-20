package top.hiccup.jdk.concurrent.locks;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Java锁分类和优化策略：
 *
 * 【优化策略】
 * 1、减小锁粒度：如JDK1.7中ConcurrentHashmap的Segment锁分段细化技术，JDK1.8版本的锁粒度更小
 * 
 * 2、锁分离（读写分离）：ReentrantReadWriteLock
 * 
 * 3、锁消除：对象逃逸分析等
 * 
 * 4、无锁：也就是乐观锁，如cas操作，java.util.concurrent.atomic包使用无锁实现，无锁队列AQS
 * 
 * 【独占锁】
 * ReentrantLock，ReentrantReadWriteLock.WriteLock
 * 
 * 【共享锁】
 * JUC包中的ReentrantReadWriteLock.ReadLock，CyclicBarrier，CountDownLatch和Semaphore等
 *
 * =====================================================================================================================
 * 为什么条件锁会产生虚假唤醒现象（spurious wakeup）？ @see https://www.zhihu.com/question/271521213
 *
 * // 推荐写法
 * while (isCondition()) {
 *      waitForAConfition(...);
 * }
 *
 * // 不推荐，可能引入 bug
 * if (isCondition()) {
 *      waitForAConfition(...);
 * }
 * =====================================================================================================================
 *
 * JDK8中JUC新引入了StampedLock
 *
 * @author wenhy
 * @date 2018/9/7
 */
public class LockTest {
}


/**
 * 简单自旋锁实现
 * 不足：
 * 1、无法重入、无法保证公平性
 * 2、CAS操作需要硬件的配合
 */
class SimpleSpinLockTest {

    private AtomicReference<Thread> lockOwner = new AtomicReference<>();

    public void lock() {
        // 如果锁未被占用，则设置当前线程为锁的拥有者
        while (!lockOwner.compareAndSet(null, Thread.currentThread())) {
        }
    }

    public void unlock() {
        // 只有锁的拥有者才能释放锁
        lockOwner.compareAndSet(Thread.currentThread(), null);
    }
}


/**
 * 排队自旋锁（公平锁），类似于现实中银行柜台的排队叫号系统
 * 不足：每个进程/线程占用的处理器都在读写同一个变量serviceNum，
 * 每次读写操作都必须在多个处理器缓存之间进行缓存同步，这会导致繁重的系统总线和内存的流量，大大降低系统整体的性能
 */
class TicketLock {
    /**
     * 当前锁服务的线程（服务号码）
     */
    private AtomicInteger serviceNum = new AtomicInteger();
    /**
     * 线程的排队号码
     */
    private AtomicInteger ticketNum = new AtomicInteger();

    public int lock() {
        // 原子性地获得一个排队号
        int myTicketNum = ticketNum.getAndIncrement();
        // 只要当前服务号不是自己的就不断轮询
        while (serviceNum.get() != myTicketNum) {
            // 自旋等待（可重入，但无法判断重入了几次，而且释放时也只直接释放）
        }
        return myTicketNum;
    }

    public void unlock(int myTicket) {
        // 只有当前锁的拥有者才能释放锁
        int next = myTicket + 1;
        serviceNum.compareAndSet(myTicket, next);
    }
}


/**
 * MCS Spinlock 是一种基于链表的可扩展、高性能、公平的自旋锁，申请线程只在本地变量上自旋，
 * 直接前驱负责通知其结束自旋，从而极大地减少了不必要的处理器缓存同步的次数，降低了总线和内存的开销。
 */
class MCSLock {
    public static class MCSNode {
        volatile MCSNode next;
        /**
         * 默认是在等待锁
         */
        volatile boolean isBlock = true;
    }

    /**
     * 指向最后一个申请锁的MCSNode
     */
    volatile MCSNode queue;
    /**
     * 作用类似于sun.misc.Unsafe类
     */
    private static final AtomicReferenceFieldUpdater UPDATER = AtomicReferenceFieldUpdater
            .newUpdater(MCSLock.class, MCSNode.class, "queue");

    public void lock(MCSNode node) {
        MCSNode predecessor = (MCSNode) UPDATER.getAndSet(this, node);
        if (predecessor != null) {
            predecessor.next = node;
            while (node.isBlock) {
                // 自旋等待
            }
        } else {
            // 只有一个线程在使用锁，没有前驱来通知它，所以得自己标记自己为非阻塞
            node.isBlock = false;
        }
    }

    public void unlock(MCSNode node) {
        // 锁拥有者进行释放锁才有意义
        if (node.isBlock) {
            return;
        }
        // 检查是否有人排在自己后面
        if (node.next == null) {
            if (UPDATER.compareAndSet(this, node, null)) {
                // compareAndSet返回true表示确实没有人排在自己后面
                return;
            } else {
                // 突然有人排在自己后面了，可能还不知道是谁，下面是等待后续者
                while (node.next == null) {
                }
            }
        }
        node.next.isBlock = false;
        node.next = null;
    }
}


/**
 * 1、CLH锁也是一种基于链表的可扩展、高性能、公平的自旋锁，申请线程只在本地变量上自旋，它不断轮询前驱的状态，如果发现前驱释放了锁就结束自旋。
 * 2、AQS就是基于CLH锁的
 * 
 * MCS来自于其发明人名字的首字母：John Mellor-Crummey和Michael Scott
 * 而CLH的发明人是：Craig，Landin and Hagersten
 *
 * MCS于CLH的差异：
 * 1、从代码实现来看，CLH比MCS要简单得多。
 * 2、从自旋的条件来看，CLH是在前驱节点的属性上自旋，而MCS是在本地属性变量上自旋。
 * 3、从链表队列来看，CLH的队列是隐式的，CLHNode并不实际持有下一个节点；MCS的队列是物理存在的。
 * 4、CLH锁释放时只需要改变自己的属性，MCS锁释放则需要改变后继节点的属性。
 *
 * 传统的“Spin lock” 和 “Ticket Lock”都在同一个共享变量上竞争（例如Spin Lock中的owner、Ticket Lock中的serviceNum）
 * 这样会给CPU缓存一致性（cache coherence）带来较大的压力，而MCS和CLH锁的优化点在于把这种竞争分散到队列中的每个节点去了。
 */
class CLHLock {
    public static class CLHNode {
        /**
         * 默认是在等待锁
         */
        private volatile boolean isLocked = true;
    }

    private volatile CLHNode tail;
    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater
            .newUpdater(CLHLock.class, CLHNode.class, "tail");

    public void lock(CLHNode node) {
        CLHNode preNode = UPDATER.getAndSet(this, node);
        if (preNode != null) {
            // 无法重入，而且这里会造成死锁
            while (preNode.isLocked) {
                // TODO 注意这几个锁都是无阻塞自旋的，所以不存在线程的唤醒
            }
        }
    }

    public void unlock(CLHNode node) {
        // 如果队列里只有当前线程，则释放对当前线程的引用（for GC）。
        if (!UPDATER.compareAndSet(this, node, null)) {
            // 改变状态，让后续线程结束自旋
            node.isLocked = false;
        }
    }
}