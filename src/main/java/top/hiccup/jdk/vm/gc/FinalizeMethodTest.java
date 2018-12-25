package top.hiccup.jdk.vm.gc;

/**
 * Object中的finalize()方法会在gc之前被调用，方法里的代码可能把对象又变为可触及的，此时gc就不会回收它
 * 不过finalize()方法每个对象只会调用一次，第二次再gc时就不会调用了
 *
 * @VM args: -XX:+PrintGCDetails
 *
 * 实践中，因为 finalize 拖慢垃圾收集，导致大量对象堆积，也是一种典型的导致 OOM 的原因。
 *
 * Java平台目前在逐步使用java.lang.ref.Cleaner替换原有的finalize实现机制，
 * Cleaner机制实际上是利用幻象引用来实现的，每个Cleaner的操作都是独立的，有自己的运行线程，可以避免意外死锁。
 *
 * @author wenhy
 * @date 2018/9/6
 */
public class FinalizeMethodTest {

    public static FinalizeMethodTest obj;

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize called..");
        obj = this;
    }

    public static void main(String[] args) throws InterruptedException {
        obj = new FinalizeMethodTest();
        System.out.println(obj);
        obj = null;
        System.gc();
        Thread.sleep(200);
        System.out.println(obj);
        obj = null;
        Runtime.getRuntime().gc();
        Thread.sleep(200);
        System.out.println(obj);
    }
}
