package top.hiccup.jdk.concurrent.thread;

/**
 * 为什么wait方法必须要获得锁（同synchronized关键字一起使用）（不放同步块里的话会抛java.lang.IllegalMonitorStateException）？
 *
 * wait方法是让调用wait方法的线程等待，暂时先把wait方法对应的对象上的锁让出来，
 * 其它线程竞争该对象的monitor，用完后再告知（notify）等待的那个线程可以继续执行了，
 * 因此，只有在synchronized块中才有意义（否则，如果大家并不遵循同步机制，
 * 那还等谁呢？根本没人排队，也就谈不上等待和唤醒了）
 *
 * 不推荐stop()是因为它不是线程安全的，会释放当前线程获取的所有锁
 * 不推荐suspend()是因为它挂起线程的时候并不会释放锁，这样非常容易导致死锁（应当用wait和notify方法来实现同步）
 * sleep()也不会释放锁
 *
 * wait方法是可以响应中断的，而线程被synchronized阻塞时是放在lock pool锁池，此时无法响应中断
 *
 * 首先明确wait和notifyAll是基于对象而存在的，wait等待的就是一个对象发出的信号
 * 既然基于对象，因此需要一个数据结构来存放这些等待的线程ID，而且这个数据结构应当与这个对象绑定，（这个数据结构容量是多少呢？）
 * 多线程环境下在这个对象上面可能有多个线程调用wait/notify方法，向这个数据结构写入，删除数据时，依然存在并发问题，理论上也需要一个锁来控制。
 * 在JVM中是通过检查当前线程是否为对象的OWNER（对象监视器）来判定是否要抛出相应的异常。
 *
 * =====================================================================================================================
 * 为什么wait和notify不是放在Thread类中呢？
 * 因为wait和notify是需要两个不同的线程调用（等待线程和通知线程），如果都放在Thread类中，调用notify的线程B无法直接直到有哪些线程调用了wait在等待
 * 所以需要借助第三方来存储多个线程的关联关系（跟计算机领域的很多场景很像，加个第三者就可以解决很多事情）
 *
 * =====================================================================================================================
 *
 * @author wenhy
 * @date 2018/9/17
 */
public class WaitAndSynchronizedTest {

    private static Object lock = new Object();

    static class AnotherThread extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("anotherThread running..");
                try {
                    Thread.sleep(100);
                    // 唤醒等待在当前对象上的线程（wait pool ==> lock pool）
                    lock.notify();
                    // motify()不会直接释放锁，而是等synchronized执行完成后
                    Thread.sleep(100);
                    System.out.println("anotherThread done..");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread anotherThread = new AnotherThread();
        anotherThread.start();
        synchronized (lock) {
            try {
                System.out.println("main Thread wait");
                // wait()会立即释放锁，且线程从wait恢复时会自动获得该对象的锁
                lock.wait();
                System.out.println("anotherThread is completed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 如果把start()放在这里，则main线程会一直阻塞等待下去
//        anotherThread.start();
        System.out.println("main Thread is completed");
    }
}
