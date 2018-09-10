package top.hiccup.schema.singleton;

/**
 * 单例模式：懒汉方式
 * （不是线程安全的：加synchronized比较影响效率，不建议使用）
 *
 * @author wenhy
 * @date 2018/1/2
 */
public class B_LazySingleton {

    private static B_LazySingleton instance = null;

    private B_LazySingleton() {
        System.out.println("懒汉单例模式初始化。。");
    }

    public static B_LazySingleton getInstance() {
//    public static synchronized B_LazySingleton getInstance() {
        if(null == instance) {
            instance = new B_LazySingleton();
        }
        return instance;
    }


    public static void main(String[] args) {
        B_LazySingleton BLazySingleton1 = B_LazySingleton.getInstance();
        B_LazySingleton BLazySingleton2 = B_LazySingleton.getInstance();
        System.out.println(BLazySingleton1.hashCode());
        System.out.println(BLazySingleton2.hashCode());
    }

}
