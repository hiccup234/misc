package com.hiccup.schema.singleton;

/**
 * 单例模式：双重检测锁方式
 * （可能会有隐患：多线程环境下获取到未初始化完全的实例对象）
 *
 * @author wenhy
 * @date 2018/1/2
 */
public class C_DoubleCheckSingleton {

    private static C_DoubleCheckSingleton instance = null;

    private C_DoubleCheckSingleton() { }

    public static C_DoubleCheckSingleton getInstance() {
        if(null == instance) {
            synchronized (C_DoubleCheckSingleton.class) {
                if(null == instance) {
                    // new C_DoubleCheckSingleton() 不是原子操作
                    // a） 在内存中分配空间，并将instance引用指向该内存空间。
                    // b） 执行对象的初始化的逻辑，完成对象的构建。
                    instance = new C_DoubleCheckSingleton();
                }
            }
        }
        //如果t1线程在赋值instance后CPU时间片用完，t2线程这时进来获取到的instance就是为完全初始化的。
        return instance;
    }


    public static void main(String[] args) {
        C_DoubleCheckSingleton CDoubleCheckSingleton1 = C_DoubleCheckSingleton.getInstance();
        C_DoubleCheckSingleton CDoubleCheckSingleton2 = C_DoubleCheckSingleton.getInstance();
        System.out.println(CDoubleCheckSingleton1.hashCode());
        System.out.println(CDoubleCheckSingleton2.hashCode());
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                C_DoubleCheckSingleton d1 = C_DoubleCheckSingleton.getInstance();
                System.out.println(d1.hashCode());
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                C_DoubleCheckSingleton d2 = C_DoubleCheckSingleton.getInstance();
                System.out.println(d2.hashCode());
            }
        });
        t1.start();
        t2.start();
    }

}
