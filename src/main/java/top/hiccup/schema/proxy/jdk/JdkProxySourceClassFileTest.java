package top.hiccup.schema.proxy.jdk;

import sun.misc.ProxyGenerator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class Cat {
    public String test() {
        return "abc";
    }
}

/**
 * 测试向磁盘写增强后的class文件
 */
public class JdkProxySourceClassFileTest {

    public static void main(String[] args) {
        String path = "C:\\Ocean\\Work";
        writeClassToDisk(path, Cat.class.getName(), new Class[]{Cat.class});
    }

    public static void writeClassToDisk(String path, String className, Class[] clazzs){
        byte[] classFile = ProxyGenerator.generateProxyClass(className, clazzs);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(classFile);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
