package top.hiccup.jdk.concurrent.tool;

import java.util.concurrent.Exchanger;

/**
 * 在两个线程之间交换数据，只能是2个线程，类似Linux的管道，只有一个重要方法exchange
 *
 * 这种设计跟程序员自己做线程共享相比有什么优势嚒？（有个交换的概念）
 *
 * @author wenhy
 * @date 2019/8/12
 */
public class ExchangerTest {

    public static void main(String[] args) {
        Exchanger<Object> exchanger = new Exchanger<>();
        // T1
        new Thread(()-> {
            Object target = new Object();
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("T1交换前：" + target);
                    target = exchanger.exchange(target);
                    System.out.println("T1交换后：" + target);
                    System.out.println("================================");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // T2
        new Thread(()-> {
            Object target = new Object();
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("T2交换前：" + target);
                    target = exchanger.exchange(target);
                    System.out.println("T2交换后：" + target);
                    System.out.println("================================");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
