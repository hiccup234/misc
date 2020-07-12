package top.hiccup.schema.design.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import top.hiccup.schema.design.proxy.BusiServiceImpl;
import top.hiccup.schema.design.proxy.IBusiService;

/**
 * JDK动态代理测试类：普通类final方法不能被动态代理
 *
 * 【注意】
 * JDK动态代理只有在外部调用其方法时才会代理调用，自己调用自己的方法不会走代理调用，即invoke只有一次机会
 * （要自己的A方法调用自己的B方法，只能通过在A中持有代理增强过后的引用来调用，this.B()是不会再次走到代理的）
 *
 * @author wenhy
 * @date 2018/1/14
 */
public class JdkProxyTest {

    public void proxy() {
        // 需要代理增强的对象（要求被代理的对象必须实现接口）
        final IBusiService target = new BusiServiceImpl();

        // 通过Proxy的静态方法newProxyInstance来动态生成代理对象（注意：这个代理对象其实是Proxy的子类的对象）
        // 这里传入ClassLoader是因为同一个类可以被不同的CL加载，那么新生成的代理对象肯定要跟被代理的对象处于同一CL中
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

        // 通过设置系统属性来保存动态代理生成的类，运行程序就会在当前classpath下新增com\sun\proxy目录，
        // 并自动保存动态代理生成的类$Proxy0.class
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        JdkProxyTest proxyTest = new JdkProxyTest();
        proxyTest.proxy();
    }
}



