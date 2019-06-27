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
