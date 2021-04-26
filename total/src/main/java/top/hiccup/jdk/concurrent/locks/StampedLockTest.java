package top.hiccup.jdk.concurrent.locks;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.StampedLock;

/**
 * JDK8新增StampedLock锁测试
 *
 * @author wenhy
 * @date 2019/4/11
 */
public class StampedLockTest {

    private static final StampedLock stampedLock = new StampedLock();

    private static final Map<String, String> data = new TreeMap();

    public static void main(String[] args) {
        new Thread(() -> {
            long stamp = stampedLock.writeLock();
            try {
                data.put("key", "abc");
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        }).start();

        new Thread(() -> {
            long stamp = stampedLock.tryOptimisticRead();
            if (!stampedLock.validate(stamp)) {
                stamp = stampedLock.readLock();
                try {
                    data.get("key");
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
        }).start();
    }
}


