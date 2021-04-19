package top.hiccup.jdk.vm.gc;

import sun.misc.Cleaner;

/**
 * CleanerTest: 很多第三方库自己直接利用幻象引用定制资源收集，比如广泛使用的 MySQL JDBC driver 之一的 mysql-connector-j，就利用了幻象引用机制
 *
 * @author wenhy
 * @date 2021/4/19
 */
public class CleanerTest implements AutoCloseable {

    static class State implements Runnable {
        @Override
        public void run() {
            // cleanup action accessing State, executed at most once
            System.out.println("幻象引用【PhantomReference】回收机制，代替Object.finalize()");
        }
    }

    private final State state;

    private final Cleaner cleaner;

    public CleanerTest() {
        this.state = new State();
        this.cleaner = Cleaner.create(this, state);
    }

    @Override
    public void close() {
        cleaner.clean();
    }
}