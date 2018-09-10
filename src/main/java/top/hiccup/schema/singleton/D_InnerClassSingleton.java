package top.hiccup.schema.singleton;

/**
 * 单例模式：静态内部类实现方式
 * （推荐使用：由JVM保证线程安全）--多个ClassLoader加载的时候会不会有问题呢？
 *
 * @author wenhy
 * @date 2018/1/2
 */
public class D_InnerClassSingleton {

    private D_InnerClassSingleton() { }

    private static class NestClass {
        private static final D_InnerClassSingleton instance = new D_InnerClassSingleton();
    }

    public static D_InnerClassSingleton getInstance() {
        // javac编译后，NestClass是一个单独的.class文件，
        // 加载D_InnerClassSingleton.class的时候不会自动加载NestClass的.class文件
        // 当调用NestClass.instance的时候才会触发JVM加载NestClass类
        return NestClass.instance;
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                D_InnerClassSingleton s1 = D_InnerClassSingleton.getInstance();
                System.out.println(s1.hashCode());
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                D_InnerClassSingleton s2 = D_InnerClassSingleton.getInstance();
                System.out.println(s2.hashCode());
            }
        });
        t1.start();
        t2.start();
    }

}
