package top.hiccup.agent;

/**
 * 程序启动之前启动代理(pre-main)
 *
 * @author wenhy
 * @date 2020/8/28
 */
public class MainInst {

    public static void main(String[] args) {
        new MainInst().test();
    }

    public void test() {
        System.out.println("Hello World!");
        System.out.println("Hello World!");
        System.out.println("Hello World!");
    }
}
