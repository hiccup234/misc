package com.hiccup.jdk.lang;

public class Object {

    private static native void registerNatives();
    static {
        registerNatives();
    }

//    // finnal的本地方法，子类不能重写
//    public final native Class<?> getClass();
//    public final native void notify();
//    public final native void notifyAll();
//    public final native void wait(long timeout) throws InterruptedException;
//
//    // 本地方法wait的包装方法
//    public final void wait(long timeout, int nanos) throws InterruptedException {
//        if (timeout < 0) {
//            throw new IllegalArgumentException("timeout value is negative");
//        }
//        if (nanos < 0 || nanos > 999999) {
//            throw new IllegalArgumentException("nanosecond timeout value out of range");
//        }
//        if (nanos >= 500000 || (nanos != 0 && timeout == 0)) {
//            timeout++;
//        }
//        wait(timeout);
//    }
//    public final void wait() throws InterruptedException {
//        wait(0);
//    }

    // 为什么hashCode是本地方法，而equals却是成员方法呢？
    public native int hashCode();

    public boolean equals(java.lang.Object obj) {
        return (this == obj);
    }

    protected native java.lang.Object clone() throws CloneNotSupportedException;

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    // 对象成员方法，对象被回收时执行的方法，一般不用来释放资源，因为GC的时机对程序员来说是不可控的
    protected void finalize() throws Throwable { }

}
