package top.hiccup.jdk.concurrent.thread;

/**
 * 测试批量创建线程并启动消耗的时间
 *
 * @author wenhy
 * @date 2019/4/12
 */
public class CreateThreadTest {

    public static void main(String[] args) {
        int size = 500;
        long start = System.currentTimeMillis();
        for (int i = 0; i<size; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int o = 234;
                    long ff = 234L;
                }
            }).start();
        }
        long time = System.currentTimeMillis() - start;
        System.out.println(time);
        System.out.println(time/500.0);
    }
}
