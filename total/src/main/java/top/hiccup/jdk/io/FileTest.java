package top.hiccup.jdk.io;

import java.io.File;
import java.net.URL;

/**
 * 文件操作
 *
 * @author wenhy
 * @date 2018/2/28
 */
public class FileTest {

    public static void main(String[] args) {
        ClassLoader classLoader = FileTest.class.getClassLoader();
        // getResource()方法会去classpath下找这个文件，获取到url
        URL url = classLoader.getResource("roster.xml");
        // url.getFile() 返回文件的绝对路径
        System.out.println(url.getFile());
        File file = new File(url.getFile());
        System.out.println(file.exists());
    }

}
