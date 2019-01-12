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
 * wait方法是可以响应中断的，而线程被synchronized阻塞时是放在lock pool的，此时无法响应中断
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
