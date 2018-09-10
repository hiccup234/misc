package top.hiccup.jdk.io;

import java.io.DataInputStream;
import java.net.URL;

/**
 * Created by wenhy on 2018/2/28.
 */
public class DataStreamTest {
    /**
     *  从读取网页读取内容
     */
    public static void main(String[] args) {
        try{
            int num;
            byte buf[] = new byte[4096];
            URL u = new URL("http://cn.bing.com");
            DataInputStream dataInputStream = new DataInputStream(u.openStream());
            while((num=dataInputStream.read(buf))!=-1){
                System.out.write(buf,0,num);
            }
//            DataInputStream dataInputStream = new DataInputStream(u.openStream());
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(dataInputStream);
//            while(bufferedInputStream.read(buf)!=-1){
//            }
//            System.out.println(new String(buf));

               Class cls = String.class;


            String str1 = new String("123");
            String str2 = new String("123");
            System.out.println(str1.hashCode());
            System.out.println(str2.hashCode());
//            Proxy proxy = new Proxy();
        } catch (Exception e){
            System.out.println("发生了"+e+"异常");
        }
    }
}

class Test {

}