package top.hiccup.jdk.lang.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/**
 * 注解@Inherited的意思是指：子类是否能继承父类的注解
 *
 * @author wenhy
 * @date 2019/3/7
 */
public class InheritedTest {
    // 表示这个注解可以被继承
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    private @interface SomeAnnotation {}

    @SomeAnnotation
    private static class Base {}

    private static class Sub extends Base {}

    public static void main(String[] args) {
        Base base = new Base();
        System.out.println(Arrays.toString(base.getClass().getAnnotations()));
        System.out.println(base.getClass().isAnnotationPresent(SomeAnnotation.class));

        Sub sub = new Sub();
        System.out.println(Arrays.toString(sub.getClass().getAnnotations()));
        System.out.println(sub.getClass().isAnnotationPresent(SomeAnnotation.class));
    }
}
