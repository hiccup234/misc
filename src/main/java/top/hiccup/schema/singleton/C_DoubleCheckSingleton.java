package top.hiccup.schema.singleton;

import java.util.HashSet;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 单例模式：双重检测锁方式
 * （可能会有隐患：多线程环境下获取到未初始化完全的实例对象 ==> 用volatile修饰可以防止）
 *
 * @author wenhy
 * @date 2018/1/2
 */
public class C_DoubleCheckSingleton {

    // 这里的instance可以选择volatile做修饰来加快高并发下的速度，尽管synchronized代码块可以保证instance的可见性

    //
    private static C_DoubleCheckSingleton instance = null;
//    private static volatile C_DoubleCheckSingleton instance = null;

    private int t = 0;

    private C_DoubleCheckSingleton() {
        long count = 0;
        for (long i=0; i<5_000_000_000L; i++) {
            count ++;
        }
        t = (int) count;
        if (0 == 0) {
            t = 1;
        }
    }

    public static C_DoubleCheckSingleton getInstance() {
        if (null == instance) {
            synchronized (C_DoubleCheckSingleton.class) {
                if (null == instance) {
                    // new C_DoubleCheckSingleton() 不是原子操作

                    // memory = allocate();    // 1:分配对象的内存空间
                    // ctorInstance(memory);   // 2:初始化对象
                    // instance = memory;      // 3:设置instance指向刚才分配的内存地址
                    // 伪代码中的2和3之间，可能会被重排序，所以如果执行顺序按1，3，2的话，线程在执行完3后时间片用完切换线程则可能出现未初始化的对象
                    // 如果用volatile来修饰则会产生写读屏障storeload，禁止2，3的指令重拍
                    instance = new C_DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {
        // 验证未初始化完全的问题
        CyclicBarrier cyclicBarrier = new CyclicBarrier(500);
        // 开500个线程，先阻塞，待所有线程创建完毕并就绪后同时执行
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                C_DoubleCheckSingleton singleton = C_DoubleCheckSingleton.getInstance();
                if (singleton.t != 1) {
                    throw new RuntimeException("检测到未初始化完全的单例对象");
                }
            }).start();
        }
    }
}
