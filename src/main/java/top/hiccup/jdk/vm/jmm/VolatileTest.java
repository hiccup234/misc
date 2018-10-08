package top.hiccup.jdk.vm.jmm;

/**
 * Volatile关键字测试
 *
 * @author wenhy
 * @date 2018/9/3
 */
public class VolatileTest extends Thread {

    private static volatile boolean stop = false;

    public void stopTest() {
        stop = true;
    }

    @Override
    public void run() {
        int i = 0;
        while (!stop) {
            System.out.println(i++);
        }
        System.out.println("Bye Bye");
    }

    public static void main(String args[]) throws InterruptedException {
        VolatileTest t1 = new VolatileTest();
        t1.start();
        Thread.sleep(100);
        VolatileTest t2 = new VolatileTest();
        t2.stopTest();
    }
}