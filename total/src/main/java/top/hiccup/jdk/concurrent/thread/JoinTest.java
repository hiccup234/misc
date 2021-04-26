package top.hiccup.jdk.concurrent.thread;

/**
 * join()方法不同于wait和notify，它是定义在Thread类中的：
 * 主线程等待子线程结束后才继续执行（意思是当前调用线程等待被调用线程执行完成再继续执行）
 *
 * @author wenhy
 * @date 2019/3/8
 */
public class JoinTest {

    public static void main(String[] args) {
        Thread t = new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();

        try {
            System.out.println("正待子线程结束..");
            // 等待子线程执行结束后再继续执行
            // join()底层其实是自旋调用了子线程对象t的wait()方法来来阻塞主线程，
            t.join();
            System.out.println("子线程结束，主线程继续执行..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
