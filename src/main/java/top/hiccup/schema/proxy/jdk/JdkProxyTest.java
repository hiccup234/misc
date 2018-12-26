package top.hiccup.schema.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 的动态代理测试
 *
 * @author wenhy
 * @date 2018/1/14
 */
public class JdkProxyTest {

    private interface IBusiService {
        String getStr();
    }

    private class BusiServiceImpl implements IBusiService {
        @Override
        public String getStr() {
            return new String("abc");
        }
    }

    public void test() {
        // 需要代理增强的对象（要求被代理的对象必须实现接口）
        final IBusiService target = new BusiServiceImpl();
        IBusiService busiServiceProxy = (IBusiService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 反射机制调用原对象方法逻辑
                        String str = (String) method.invoke(target, args);
                        return str.toUpperCase();
                    }
                });
        System.out.println(busiServiceProxy.getStr());
    }

    public static void main(String[] args) {
        JdkProxyTest proxyTest = new JdkProxyTest();
        proxyTest.test();
    }
}



