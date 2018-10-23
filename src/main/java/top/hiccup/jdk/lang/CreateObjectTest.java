package top.hiccup.jdk.lang;

import top.hiccup.util.ObjectUtil;
import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * 创建对象的5种常见方式
 *
 * @author wenhy
 * @date 2018/8/23
 */
public class CreateObjectTest {

    static class Foo implements Cloneable, Serializable {
        @Override
        public Foo clone() {
            Foo foo = null;
            try {
                foo = (Foo)super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return foo;
        }
    }
    static class Foo2 {
        public Foo2() {}
    }

    public static void main(String[] args) {
        // 1.通过new关键字创建
        Foo foo = new Foo();
        System.out.println(foo);

        // 2.通过反射机制创建
        try {
            Foo foo2 = Foo.class.newInstance();
            System.out.println(foo2);
//            foo2 = Foo.class.getConstructor().newInstance();
//            System.out.println(foo2);
            // Class.newInstance可以访问到所有以定义的构造器
            // 而Class.getConstructor().newInstance()只能访问到公开的public构造器
            Foo2 f = Foo2.class.getConstructor().newInstance();
            System.out.println(f);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 3.通过Object.clone方法创建：浅拷贝
        Foo foo3 = (Foo)foo.clone();
        System.out.println(foo3);

        // 4.通过序列化和反序列化创建：深拷贝
        Foo foo4 = ObjectUtil.deepClone(foo);
        System.out.println(foo4);

        // 5.通过Unsafe.allocateInstance方法创建
        try {
            // 会有VM.isSystemDomainLoader的判断，应用代码不能直接这样使用
            // Unsafe.allocateInstance方法创建的对象没有初始化实例字段
            Foo2 foo5 = (Foo2)Unsafe.getUnsafe().allocateInstance(Foo2.class);
            System.out.println(foo5);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
