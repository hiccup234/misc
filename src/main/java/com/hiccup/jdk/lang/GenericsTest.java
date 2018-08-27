package com.hiccup.jdk.lang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Java泛型：参数化类型
 *
 * 泛型按照使用情况可以分为3种：
 * 1. 泛型类
 * 2. 泛型接口
 * 3. 泛型方法
 *
 * 出于规范的目的，Java 还是建议我们用单个大写字母来代表类型参数。常见的如：
 * 1. T 代表一般的任何类
 * 2. E 代表 Element 的意思，或者 Exception 异常的意思
 * 3. K 代表 Key 的意思
 * 4. V 代表 Value 的意思，通常与 K 一起配合使用
 * 5. S 代表 Subtype 的意思
 *
 * 【通配符】
 * <?> 无限定通配符
 * <? extends T> 有上限的通配符
 * <? super T> 有下限的通配符
 *
 * 【类型擦除】
 *
 *
 *
 * @author wenhy
 * @date 2018/8/27
 */
public class GenericsTest<T> {

    public void testGenericLabel(Collection<?> collection) {
        // 通配符 ? 表示它是未知的参数类型，在当前方法内部所有涉及到泛型的方法编译都通不过
        // 所以通常说<?>提供了只读功能
//        collection.add(123);
//        collection.add("hello");
        collection.size();
        collection.iterator().hasNext();
    }

    public <T> void testGenericLabel2(Collection<T> collection) {
        // 注意：这里泛型方法里的T跟泛型类上的T没有任何关系
        // 采用类型参数是可以进行写操作的，需要强制类型转换
        collection.add((T) new Integer(123));
        collection.add((T)"hello");
    }

    public void testGenericLabel3(Collection<? extends Runnable> collection) {
        // 有上限的通配符也不能写
//        collection.add(new Thread());
        collection.stream().forEach((r) -> System.out.println(r));
    }

    public void testGenericLabel4(Collection<? super String> collection) {
        // 有下限的通配符能实现特定的写功能，这是因为从?到String形成了一个区间，编译器可以匹配到确定的下限类型
//        collection.add(new Object());
        collection.add("abc");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    // 类型擦除
    /////////////////////////////////////////////////////////////////////////////////////////////
    class Erasure<T> {
        T object;
        public Erasure(T object) {
            this.object = object;
        }
        public void add(T object) { }
    }
    class Erasure2<T extends String> {
        T object;
        public Erasure2(T object) {
            this.object = object;
        }
        public void add(T object) { }
    }
    public void testErasure() {
        Erasure<String> e1 = new Erasure<>("abc");
        Class clazz = e1.getClass();
        System.out.println("Erasure " + clazz.getName());
        for(Field field : clazz.getDeclaredFields()) {
            System.out.println("Erasure " + field.getName() + " : " + field.getType().getName());
        }
        for(Method method : clazz.getDeclaredMethods()) {
            System.out.println("Erasure " + method.toString());
        }
        // Java编译器在编译的时候会进行类型擦除（类型转译）
        // 如果泛型没有指定上限则会被转译为Object
        // 如果泛型指定了上限如String，则会被转译为String类型
        Erasure2<String> e2 = new Erasure2<>("abc");
        Class clazz2 = e2.getClass();
        System.out.println("Erasure2 " + clazz2.getName());
        for(Field field : clazz2.getDeclaredFields()) {
            System.out.println("Erasure2 " + field.getName() + " : " + field.getType().getName());
        }
        for(Method method : clazz2.getDeclaredMethods()) {
            System.out.println("Erasure2 " + method.toString());
        }

        // 利用反射绕过泛型限制
        List<Integer> list = new ArrayList<>(4);
        list.add(123);
        try {
            Method method = list.getClass().getDeclaredMethod("add", Object.class);
            method.invoke(list, "abc");
            method.invoke(list, new Thread());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // Lambda的限制
//        list.stream().forEach(System.out::println);
        for(Object o : list) {
            System.out.println(o);
        }
    }

    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>(2);
        List<String> list2 = new ArrayList<>(2);
        System.out.println(list1.getClass() == list2.getClass());
        // List<Integer>与List<String>编译后的字节码在JVM中类型都是java.util.ArrayList
        System.out.println(list1.getClass().getName());
        System.out.println(list2.getClass().getName());

        GenericsTest<String> main = new GenericsTest<>();
        main.testErasure();
    }

}
