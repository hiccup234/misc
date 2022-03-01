package top.hiccup.schema.design.singleton;

/**
 * 单例模式：饥饿方式
 * （线程安全：类加载时默认初始化，最简单但也有一定的局限性，可能类加载时某些资源还未准备好）
 * <p>
 * 使用场景：
 * 1、工具类对象
 * 2、系统中只能存在一个实例的类
 * 3、创建频繁或又耗时耗资源且又经常用到的对象
 * <p>
 * 如：JDK的Runtime类就是饥饿单例
 * Spring容器的实例Bean默认也是饥饿单例，在容器启动时初始化，也可以设置为懒汉式（default-lazy-init="true"）
 *
 * @author wenhy
 * @date 2018/1/2
 */
public class A_HungrySingleton {

    private static final A_HungrySingleton instance = new A_HungrySingleton();

//    static {
//        instance = new A_HungrySingleton();
//    }

    private A_HungrySingleton() {
        // 一定要记得私有化构造器
    }

    public static A_HungrySingleton getInstance() {
        return instance;
    }


    public static void main(String[] args) {
        A_HungrySingleton AHungrySingleton1 = A_HungrySingleton.getInstance();
        A_HungrySingleton AHungrySingleton2 = A_HungrySingleton.getInstance();
        System.out.println(AHungrySingleton1.hashCode());
        System.out.println(AHungrySingleton2.hashCode());
        System.out.println(AHungrySingleton1 == AHungrySingleton2);
    }
}
