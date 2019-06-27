package top.hiccup.misc.interview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 美团面试题：“一个线程OOM后，其他线程还能运行吗？”
 *
 * Java中OOM又分很多类型，比如：
 * 堆溢出（“java.lang.OutOfMemoryError: Java heap space”）
 * 永久代溢出（“java.lang.OutOfMemoryError:Permgen space”），1.7后改为元数据空间：Meta space
 * 不能创建线程（“java.lang.OutOfMemoryError:Unable to create new native thread”）
 *
 * 当一个线程抛出OOM异常后，它所占据的内存资源（堆空间和栈空间）会全部被释放掉，从而不会影响其他线程的运行!
 *
 * 总结：其实发生OOM的线程一般情况下会被终结掉，该线程持有的对象占用的heap都会被gc。
 * 但是因为发生OOM之前要进行gc，就算其他线程能够正常工作，也会因为频繁gc而产生较大的影响。
 *
 *
 * @VM args: -Xms16m -Xmx16m
 *
 * @author wenhy
 * @date 2019/6/27
 */
public class 线程OOM后其他线程还能执行吗 {

    public static void main(String[] args) {
        new Thread(() -> {
            List<byte[]> list = new ArrayList<byte[]>();
            while (true) {
                System.out.println(new Date().toString() + Thread.currentThread());
                byte[] b = new byte[1024 * 1024 * 1];
                list.add(b);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                System.out.println(new Date().toString() + Thread.currentThread());
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
