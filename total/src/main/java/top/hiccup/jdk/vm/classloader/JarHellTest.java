package top.hiccup.jdk.vm.classloader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Jar Hell （jar 冲突） 检查
 *
 * @author wenhy
 * @date 2021/8/21
 */
public class JarHellTest {

    public static void main(String[] args) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String resourceName = "net/sf/cglib/proxy/MethodInterceptor.class";
            Enumeration<URL> urls = classLoader.getResources(resourceName);
            while(urls.hasMoreElements()){
                System.out.println(urls.nextElement());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
