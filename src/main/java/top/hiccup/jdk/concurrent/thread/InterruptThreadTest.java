package top.hiccup.jdk.concurrent.thread;

/**
 * 线程中断测试：目标线程必须要有响应中断的逻辑
 *
 * @author wenhy
 * @date 2018/9/22
 */
public class InterruptThreadTest {

    public static void test1() {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("haha");
            }
        });
        t.start();
        // 虽然对目标线程发起了interrupt，但是目标线程并没有响应中断的逻辑，所以中断无效
        t.interrupt();
    }

    public static void test2() {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("haha");
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("子线程结束");
                    return ;
                }
            }
        });
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }

    public static void test3() {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("haha");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("子线程休眠被中断，线程接着会不会退出呢？");
                }
                // 子线程不会退出，见Thread.sleep方法注释，抛出异常时会清除线程中断标志
                /**
                 * @throws  InterruptedException
                 *          if any thread has interrupted the current thread. The
                 *          <i>interrupted status</i> of the current thread is
                 *          cleared when this exception is thrown.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("子线程结束");
                    return ;
                }
            }
        });
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }

    public static void test4() {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("haha");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("子线程休眠被中断，线程接着会不会退出呢？");
                    // 在处理异常后发起对当前线程的中断，重新设置中断标志
                    Thread.currentThread().interrupt();
                }
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("子线程结束");
                    return ;
                }
            }
        });
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }
}
