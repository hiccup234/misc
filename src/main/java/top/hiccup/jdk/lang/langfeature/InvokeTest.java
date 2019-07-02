package top.hiccup.jdk.lang.langfeature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 通过反射来确认方法调用匹配
 *
 * @author wenhy
 * @date 2019/7/2
 */
public class InvokeTest {

    public void f(Object a, String b) {
        System.out.println("f1");
    }

    public void f(String a, Object b) {
        System.out.println("f2");
    }

    void invoke(Object obj, Object... args) {
        System.out.println("invoke1");
    }

    void invoke(String s, Object obj, Object... args) {
        System.out.println("invoke2");
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InvokeTest test = new InvokeTest();
//        test.f(null, null);
        Method method = InvokeTest.class.getMethod("f", new Class[]{Object.class, Object.class});
        method.invoke(test, null, null);

        // 调用第二个 invoke 方法
        test.invoke(null, 1);
        // 调用第二个 invoke 方法
        test.invoke(null, 1, 2);
        // 只有手动绕开可变长参数的语法糖，才能调用第一个 invoke 方法
        test.invoke(null, new Object[]{1});
    }
}
