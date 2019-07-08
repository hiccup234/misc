package top.hiccup.jdk.lang.reflect;

import java.lang.reflect.Method;

/**
 * 反射机制实现原理：
 *
 * 采用委派模型（DelegatingMethodAccessorImpl），可以在本地实现与动态实现间切换
 *
 * 【本地实现】
 * 　通过本地方法实现，反射慢也是因为需要先调本地方法的C++代码，再调Java代码
 *
 * 【动态实现】
 * 　动态生成字节码，使用invoke指令来调用目标方法，动态实现效率比本地实现快20倍左右
 * 　但是因为生成字节码比较耗时，所以仅调用几次的话反而性能不好
 *
 * @VM args: -Dsun.reflect.inflationThreshold=32 来调整反射调用次数的阈值
 *        小于32次则采用本地实现，超过32次则采用动态实现，默认值为ReflectionFactory.inflationThreshold = 15
 * @VM args: -Dsun.reflect.noInflation=true 可以关闭Inflation，使得反射一开始就采用动态实现
 *
 *
 * # 支持反射机制的语言有：Python, Lua, C#, Java等
 *
 * @author wenhy
 * @date 2018/8/12
 */
public class ReflectImplTest {

    /**
     * 反射调用目标方法
     */
    public static void target(int i) throws InterruptedException {
        // 这样可以打印栈轨迹（JVM自身创建Exception及部分不可见栈轨迹看不到）
        new Exception("#"+i).printStackTrace();
        Thread.sleep(100);
        System.out.println("==================================");
    }

    public static void test() {
        try {
            // 1.普通的方法调用
            ReflectImplTest.target(1);

            // 2.通过反射调用
            Class<?> clazz = Class.forName("top.hiccup.jdk.lang.reflect.ReflectImplTest");
            Method method = clazz.getMethod("target", int.class);
            // method.invoke不会再抛出原来方法签名上的异常，而是抛出经过统一包装的 InvocationTargetException
            for(int i=0; i<20; i++) {
                // 循环15次后可以看到DelegatingMethodAccessorImpl的字段delegate实现
                // 由之前的NativeMethodAccessorImpl已变为GeneratedMethodAccessor1
                System.out.println("i:"+i);
                method.invoke(null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void target2(int i) { }

    public static void test2() {
        try {
            Class<?> clazz = Class.forName("top.hiccup.jdk.lang.reflect.ReflectImplTest");
            Method method = clazz.getMethod("target2", Integer.TYPE);
            long startTime = System.currentTimeMillis();
            for(int i=1; i<2_000_000_000; i++) {
                // 每一亿次打印时间
                if(i % 100_000_000 == 0) {
                    long t = System.currentTimeMillis();
                    System.out.println(t - startTime);
                    startTime = t;
                }
                // 一亿次直接调用平均耗时在120ms左右
                target2(256);
            }
            System.out.println("======================================");
            for(int i=1; i<2_000_000_000; i++) {
                if(i % 100_000_000 == 0) {
                    long t = System.currentTimeMillis();
                    System.out.println(t - startTime);
                    startTime = t;
                }
                // 一亿次反射调用平均耗时在380毫秒左右
                // 这里invoke方法是个变长参Object[]，传256需要自动装箱
                method.invoke(null, 256);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 反射原理测试
        test();

        // 反射性能优化测试，考虑method.invoke的变长参和noInflation优化
        test2();
    }
}
