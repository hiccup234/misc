package top.hiccup.jdk.concurrent.locks;

/**
 * Object中wait和notify方法必须配合synchronized使用：
 *
 * 结合线程状态切换，源码见：objectMonitor.cpp
 *
 * notify的时候要唤醒其他线程，如果wait和notify不配合synchronized使用，怎么知道该唤醒谁呢？
 *
 * @author wenhy
 * @date 2022/2/27
 */
public class WaitNotifyTest {


    public synchronized void f() throws InterruptedException {
            // 将当前线程放入WaitSet等待队列中，wait会立即释放锁，而sleep,yield不会
            // 如果有线程竞争锁失败是放入同步队列（阻塞队列中），Monitor.exit时会通知从阻塞队列中取出线程然后Monitor.enter竞争
            this.wait();
    }

    public synchronized void f2() {
        // 会等同步块执行完成后释放锁
        this.notify();

        // 唤醒WaitSet等待队列中的所有线程，将它们加入到同步队列中，而notify只会取出等待队列的第一个线程加入同步队列
        this.notifyAll();

        // doSomething..
    }
}
