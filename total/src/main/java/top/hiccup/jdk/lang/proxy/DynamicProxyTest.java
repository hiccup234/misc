package top.hiccup.jdk.lang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Q: 动态代理基于什么原理？
 * A: JDK动态代理主要基于Java提供的反射机制，其他实现方式也有：ASM(直接操作字节码)，cglib(基于ASM)，Javassist等
 *
 * @author wenhy
 * @date 2018/12/26
 */
public class DynamicProxyTest {

    public static void main(String[] args) {
        // 代理目标对象
        HelloImpl hello = new HelloImpl();
        MyInvocationHandler handler = new MyInvocationHandler(hello);
        // 构造代码实例
        Hello proxyHello = (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(), handler);
        // 调用代理方法
        proxyHello.sayHello();
    }
}


interface Hello {
    void sayHello();
}

class HelloImpl implements Hello {
    @Override
    public void sayHello() {
        System.out.println("Hello World");
    }
}

class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 增强逻辑
        System.out.println("enhance Hello before");
        // 调用原目标对象的方法
        Object result = method.invoke(target, args);
        // 增强逻辑
        System.out.println("enhance Hello after");
        return result;
    }
}