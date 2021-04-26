package top.hiccup.jdk.vm;

/**
 * JVM 优雅停机
 *
 * @author wenhy
 * @date 2019/2/22
 */
public class ShutdownHookTest {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(
            () -> {
                System.out.println("开始停机");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("停机");
            }));
        System.out.println("Hello World..");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
