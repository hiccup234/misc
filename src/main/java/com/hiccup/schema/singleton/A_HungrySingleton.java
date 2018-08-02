package com.hiccup.schema.singleton;

/**
 * 单例模式：饥饿方式
 * （线程安全：类加载时默认初始化，最简单但也有一定的局限性，可能类加载时某些资源还未准备好）
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
        // 记得要私有化构造器
    }

    public static A_HungrySingleton getInstance() {
        return instance;
    }


    /** 测试代码 */
    public static void main(String[] args) {
        A_HungrySingleton AHungrySingleton1 = A_HungrySingleton.getInstance();
        A_HungrySingleton AHungrySingleton2 = A_HungrySingleton.getInstance();
        System.out.println(AHungrySingleton1.hashCode());
        System.out.println(AHungrySingleton2.hashCode());
    }

}
