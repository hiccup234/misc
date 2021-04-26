package top.hiccup.jdk.vm.primitive;

/**
 * JVM中boolean,byte,char,short作为本地变量存放在栈帧上时
 * 是作为整型来运算的（引用也是作为整型计算，long和double是两个数组元素，定长数组方便直接用下标访问，空间换时间，
 * 32位JDK一个数组元素为4字节，64位的为8字节，long和double变成16字节，但是HotSpot并没有使用高八字节）
 * 而作为成员变量或数组时放在堆上是以本身的值域宽度来存储的（1字节，1字节，2字节，2字节）
 * <p>
 * ===================================================================================
 * 引申出面试题：
 * if(flag) 与 if(true == flag) 的区别是什么？
 * 第一句判断flag是否为非0整型
 * 第二句判断flag是否等于1
 * ===================================================================================
 *
 * @author wenhy
 * @date 2018/8/1
 */
public class VmBooleanTest {

    /**
     * 用字节码工具amstools.jar反编译VmBooleanTest.class文件
     * javac Foo.java
     * java Foo
     * java -cp[-classpath] ./asmtools.jar org.openjdk.asmtools.jdis.Main VmBooleanTest.class > VmBooleanTest.jasm
     * [修改VmBooleanTest.jasm中第一个iconst_1为iconst_2]
     * java -cp ./asmtools.jar org.openjdk.asmtools.jasm.Main Foo.jasm
     * java Foo
     * [观察两次运行结果]
     */

    public static void main(String[] args) {
        boolean flag = true;
        if (flag) {
            System.out.println("Hello Java");
        }
        if (true == flag) {
            System.out.println("Hello JVM");
        }
    }

}
