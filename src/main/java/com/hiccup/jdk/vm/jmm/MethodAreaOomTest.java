package com.hiccup.jdk.vm.jmm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法区（元数据空间）内存溢出测试
 *
 * @VM args ：-XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M -XX:+PrintGCDetails    （JDK1.8）
 *
 * @author wenhy
 * @date 2018/8/17
 */
public class MethodAreaOomTest {

    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            // cglib通过继承目标类来实现增强
            enhancer.setSuperclass(JmmTest.class);
            // 这里不使用缓存来强制每次都新加载增强类
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object arg0, Method arg1, Object[] arg2,
                                        MethodProxy arg3) throws Throwable {
                    return arg3.invokeSuper(arg0, arg2);
                }
            });
            Object enhancedObj = enhancer.create();
            System.out.println(enhancedObj.getClass().getName());
        }
    }

}
