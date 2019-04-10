package top.hiccup.jdk.concurrent;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * synchronized 底层原理：
 * 
 * 锁的状态总共有四种：无锁状态、偏向锁、轻量级锁和重量级锁。
 * 锁升级顺序：偏向锁 --> 轻量级锁 --> 重量级锁 （分别对应了锁只被一个线程持有、不同线程交替持有锁、多线程竞争锁三种情况）
 * 【偏向锁】
 * 引入偏向锁是为了在无锁竞争的情况下尽量减少不必要的轻量级锁的执行路径，可以用-XX:-UseBiasedLocking禁用偏向锁
 * 注意：一个锁对象仅能被某个一个线程偏向一次，且同步块代码结束后不会自动撤销偏向锁，如果有其他线程请求锁，则会触发锁升级
 *
 * 【轻量级锁】
 * 适用于多个线程轮流获取锁，不会出现锁被占用的情况下，有其他线程来竞争锁，这样就也不会造成线程阻塞
 *
 * 【重量级锁】
 * 传统的锁（重量级锁）依赖于系统的同步函数，
 * 在linux上是使用mutex互斥锁，最底层实现依赖于futex，这些同步函数都涉及到用户态和内核态的切换、进程的上下文切换，开销成本较高。
 * 对于加了synchronized关键字但运行时并没有或较少线程竞争，或两个线程接近于交替执行的情况，使用传统锁机制无疑效率是会比较低的。
 *
 * 在JDK 1.6之前，synchronized只有传统的锁机制，因此给开发者留下了synchronized关键字相比于其他同步机制性能不好的印象。
 * 在JDK 1.6引入了两种新型锁机制：偏向锁和轻量级锁，它们的引入是为了解决在没有多线程竞争或基本没有竞争的场景下因使用传统锁机制带来的性能开销问题。
 * 优化的总思路就是尽量不要让JVM跟操作系统底层进行交互，因为JVM线程是直接映射的操作系统的线程，所以如果竞争锁失败而导致线程阻塞挂起的成本很高。
 *
 * =====================================================================================================================
 * 轻量级加锁过程：
 *    （1）在代码进入同步块的时候，如果同步对象锁状态为无锁状态（锁标志位为“01”状态，是否为偏向锁为“0”），
 *    虚拟机首先将在当前线程的栈帧中建立一个名为锁记录（Lock Record）的空间，用于存储锁对象目前的Mark Word的拷贝，官方称之为 Displaced Mark Word。
 * 　　（2）拷贝对象头中的Mark Word到锁记录中。
 * 　　（3）拷贝成功后，虚拟机将使用CAS操作尝试将对象的Mark Word更新为指向Lock Record的指针，
 *    并将Lock record里的owner指针指向object mark word。如果更新成功，则执行步骤（3），否则执行步骤（4）。
 * 　　（4）如果更新成功，那么这个线程就拥有了该对象的锁，并且将对象Mark Word的锁标志位设置为“00”，即表示此对象处于轻量级锁定状态，
 * 　　（5）如果更新失败（MarkWord已经被其他对象先一步修改了），虚拟机首先会检查对象的Mark Word是否指向当前线程的栈帧，（这里为什么还要再检查一次呢？）
 *    如果是就说明当前线程已经拥有了这个对象的锁，那就可以直接进入同步块继续执行。
 *    否则说明有其他线程已经CAS竞争到锁，轻量级锁就要自旋膨胀为重量级锁，锁标志的状态置为“10”，Mark Word中存储的就是指向重量级锁（互斥量）的指针，
 *    后面等待锁的线程也要进入阻塞状态，而当前线程便尝试使用自旋来获取锁，自旋就是为了不让线程阻塞，而采用循环去获取锁的过程。
 * 轻量级解锁过程：
 *    （1）通过CAS操作尝试把线程中复制的Displaced Mark Word对象替换到当前锁对象的Mark Word。
 * 　　（2）如果替换成功，整个同步过程就完成了。
 * 　　（3）如果替换失败，说明有其他线程尝试过获取该锁（此时锁已膨胀，所以MarkWord内容已不是本线程栈帧的锁记录），那就要在释放锁的同时，唤醒被挂起的线程。
 *
 * 偏向锁加锁过程：
 *    （1）访问Mark Word中偏向锁的标识是否为1，锁标志位是否为01，如果是则确认为可偏向状态。
 * 　　（2）如果为可偏向状态，则测试线程ID是否指向当前线程，如果是，进入步骤（5），否则进入步骤（3）。
 * 　　（3）如果线程ID并未指向当前线程，则通过CAS操作竞争锁。如果竞争成功，则将Mark Word中线程ID设置为当前线程ID，然后执行（5）；如果竞争失败，执行（4）。
 * 　　（4）如果CAS获取偏向锁失败，则表示有竞争。当到达全局安全点（safepoint）时获得偏向锁的线程被挂起，偏向锁升级为轻量级锁，然后被阻塞在安全点的线程继续往下执行同步代码。
 * 　　（5）执行同步代码。
 * 偏向锁的撤销：
 * 　　（1）偏向锁只有遇到其他线程尝试竞争偏向锁时（一旦出现多线程竞争的情况就必须撤销偏向锁），线程不会主动去释放偏向锁。
 *    （2）偏向锁的撤销，需要等待全局安全点（在这个时间点上没有字节码正在执行，会导致STW），它会首先暂停拥有偏向锁的线程，判断锁对象是否处于被锁定状态，
 *    （3）撤销偏向锁后恢复到未锁定（标志位为“01”）或轻量级锁（标志位为“00”）的状态。
 *
 * =====================================================================================================================
 * 【自旋锁】
 * 获取轻量级锁CAS失败后，JVM还会进行适应性的自旋，例如循环多次来CAS，如果都失败了再膨胀为重量级锁，并阻塞当前线程。
 *
 * 【锁粗化】
 * 将多次连续的加锁、解锁操作合并为一次，将多个连续的锁扩展成一个范围更大的锁。如：StringBuffer连续多次append，则JVM可能会检测到并进行锁粗化。
 *
 * 【锁消除】
 * 跟据逃逸分析技术，判断代码段中创建的对象是否会逃逸出当前作用域范围，如果不会逃逸，那么可以认为是线程安全的，直接消除锁。
 * @VM args: -XX:+DoEscapeAnalysis -XX:-EliminateLocks
 *
 * =====================================================================================================================
 * ##JVM中的锁也是能降级的，只不过条件很苛刻（见极客时间杨晓峰老师文章）
 * @see https://time.geekbang.org/column/article/9042
 *
 * @author wenhy
 * @date 2019/1/2
 */
public class SynchronizedTest {

    /**
     * 对于synchronized方法而言，javac会为其生成一个ACC_SYNCHRONIZED关键字，
     * 在JVM进行方法调用时，发现调用的方法被ACC_SYNCHRONIZED修饰，则会先尝试获得锁。
     * 
     * javap -v SynchronizedTest（javap -v 看不到private修饰的方法）
     */
    public synchronized void sync1() {
        System.out.println("Hello");
    }

    /**
     * 关键字修饰的代码块在javac编译时，会生成对应的monitorenter和monitorexit指令分别对应synchronized同步块的进入和退出，
     * 注意，反编译后发现有两个monitorexit指令的原因是：为了保证抛异常的情况下也能释放锁，
     * 所以javac为同步代码块添加了一个隐式的try-finally，在finally中会调用monitorexit命令释放锁
     */
    public void sync2() {
        synchronized (this) {
            System.out.println("Hello2");
        }
    }

    //==================================================================================================================
    private static String getLongBinaryString(long num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            if ((num & 1) == 1) {
                sb.append(1);
            } else {
                sb.append(0);
            }
            num = num >> 1;
        }
        return sb.reverse().toString();
    }
    private static void printf(String str) {
        System.out.printf("%100s%n", str);
    }
    /**
     *  TODO JDK8偏向锁的特性默认是打开的，但是出于性能（启动时间）考虑，在JVM启动后的的头4秒钟这个feature是被禁止的。
     *  TODO 这也意味着在此期间，prototype MarkWord会将它们的bias位设置为0，以禁止实例化的对象被偏向。
     *  TODO 4秒钟之后，所有的prototype MarkWord的bias位会被重设为1，如此新的对象就可以被偏向锁定了。
     *
     *  @VM args 开启偏向锁：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0 （直接开启，JVM不等待）
     *  @VM args 关闭偏向锁：-XX:-UseBiasedLocking
     */
    public static void main(String[] args) throws Exception {
        Thread.sleep(5000);
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        final Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        final Object lock = new Object();
        // TODO 64位JDK对象头为 64bit = 8Byte
        // 如果不执行hashCode方法，则对象头的中的hashCode默认为0
        // 但是如果执行了hashCode（identity hashcode，重载过的不受影响），会导致偏向锁的标识位变为0（不可偏向状态），
        // 且后续的加锁不会走偏向锁而是直接到轻量级锁（被hash的对象不可被用作偏向锁）
        printf("无锁状态：" + getLongBinaryString(unsafe.getLong(lock, 0L)));
//        lock.hashCode();
        // 获取对象中offset偏移地址对应的long型field的值
        printf("无锁状态：" + getLongBinaryString(unsafe.getLong(lock, 0L)));

        // 无锁 --> 偏向锁
        new Thread(() -> {
            synchronized (lock) {
                printf("偏向锁：" +getLongBinaryString(unsafe.getLong(lock, 0L)));
                printf("线程ID：" +getLongBinaryString(Thread.currentThread().getId()));
            }
            // 再次进入同步快，lock锁还是偏向当前线程
            synchronized (lock) {
                printf("偏向锁：" +getLongBinaryString(unsafe.getLong(lock, 0L)));
                printf("线程ID：" +getLongBinaryString(Thread.currentThread().getId()));
            }
        }).start();
        Thread.sleep(1000);

        printf("偏向线程结束：" +getLongBinaryString(unsafe.getLong(lock, 0L)));

        // 偏向锁 --> 轻量级锁
        synchronized (lock) {
            // 对象头为：指向线程栈中的锁记录指针
            printf("轻量级锁1：" + getLongBinaryString(unsafe.getLong(lock, 0L)));
        }
        new Thread(() -> {
            synchronized (lock) {
                printf("轻量级锁2：" +getLongBinaryString(unsafe.getLong(lock, 0L)));
            }
        }).start();
        Thread.sleep(1000);

        // 轻量级锁 --> 重量级锁
        synchronized (lock) {
            int i = 123;
            // 注意：轻量级锁1 和 轻量级锁3 的对象头是一样的，证明线程释放锁后，栈帧中的锁记录并未清除
            printf("轻量级锁3：" + getLongBinaryString(unsafe.getLong(lock, 0L)));
            // 锁膨胀
            new Thread(() -> {
                synchronized (lock) {
                    printf("重量级锁：" +getLongBinaryString(unsafe.getLong(lock, 0L)));
                }
            }).start();
            // 同步快中睡眠1秒，不会释放锁，等待子线程请求锁失败导致锁膨胀（见轻量级加锁过程）
            Thread.sleep(1000);
        }
        Thread.sleep(500);
    }
}
