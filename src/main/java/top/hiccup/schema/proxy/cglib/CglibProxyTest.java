package top.hiccup.schema.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by wenhy on 2018/1/14.
 */
public class CglibProxyTest {

    /**
     * Cglib 的动态代理测试(通过继承实现，不要求被代理对象实现接口)
     */
    private class MyCallBack implements MethodInterceptor {
        private IBusiService busiService;
        public MyCallBack(IBusiService busiService) {
            this.busiService = busiService;
        }
        // 实现“成员方法拦截器”方法
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            String str = (String)method.invoke(this.busiService, objects);
            return str.toUpperCase();
        }
    }

    private class MyCglibFactory {
        public IBusiService produce(IBusiService busiService) {
            Enhancer enhancer = new Enhancer();
            // 指定目标类
            enhancer.setSuperclass(IBusiService.class);
//            enhancer.setSuperclass(BusiSeviceWithNoIntf.class);
            // 设置回调对象接口
            enhancer.setCallback(new MyCallBack(busiService));
            return (IBusiService) enhancer.create();
        }
    }

    private class MyCallBack2 implements MethodInterceptor {
        private BusiSeviceWithNoIntf busiSeviceWithNoIntf;
        public MyCallBack2(BusiSeviceWithNoIntf busiSeviceWithNoIntf) {
            this.busiSeviceWithNoIntf = busiSeviceWithNoIntf;
        }
        // 实现“成员方法拦截器”方法
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            String str = (String)method.invoke(this.busiSeviceWithNoIntf, objects);
            return str.toUpperCase();
        }
    }
    private class MyCglibFactory2 {
        public BusiSeviceWithNoIntf produce(BusiSeviceWithNoIntf busiSeviceWithNoIntf) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(BusiSeviceWithNoIntf.class);
            enhancer.setCallback(new MyCallBack2(busiSeviceWithNoIntf));
            return (BusiSeviceWithNoIntf) enhancer.create();
        }
    }

    public void test() {
        // 需要被增强的对象（有接口）
        IBusiService busiService = new BusiServiceImpl();
        // 需要被增强的对象（没接口）
        BusiSeviceWithNoIntf busiSeviceWithNoIntf = new BusiSeviceWithNoIntf();

        MyCglibFactory myCgLibFactory = new MyCglibFactory();
        IBusiService busiServiceProxy = myCgLibFactory.produce(busiService);
        System.out.println(busiServiceProxy.getStr());

        MyCglibFactory2 myCgLibFactory2 = new MyCglibFactory2();
        BusiSeviceWithNoIntf busiSeviceWithNoIntfProxy = myCgLibFactory2.produce(busiSeviceWithNoIntf);
        System.out.println(busiSeviceWithNoIntfProxy.getStr());
    }

    public static void main(String[] args) {
        CglibProxyTest cglibProxyTest = new CglibProxyTest();
        cglibProxyTest.test();
    }

}
