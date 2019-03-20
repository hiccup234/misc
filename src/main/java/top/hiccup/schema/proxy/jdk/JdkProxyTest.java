package top.hiccup.schema.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import top.hiccup.schema.proxy.BusiServiceImpl;
import top.hiccup.schema.proxy.IBusiService;

/**
 * JDK动态代理测试类：普通类final方法不能被动态代理
 *
 * 【注意】
 * JDK动态代理只有在外部调用其方法时才会代理调用，自己调用自己的方法不会走代理调用，即invoke只有一次机会
 *
 * @author wenhy
 * @date 2018/1/14
 */
public class JdkProxyTest {

    public void test() {
        // 需要代理增强的对象（要求被代理的对象必须实现接口）
        final IBusiService target = new BusiServiceImpl();
        IBusiService busiServiceProxy = (IBusiService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("代理方法：" + method.getName());
                        // 反射机制调用原对象方法逻辑
                        String str = (String) method.invoke(target, args);
                        return str.toUpperCase();
                    }
                });
        System.out.println(busiServiceProxy.getName());
        System.out.println(busiServiceProxy.defaultMethod());
    }

    public static void main(String[] args) {
        JdkProxyTest proxyTest = new JdkProxyTest();
        proxyTest.test();
    }
}



