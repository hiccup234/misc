package top.hiccup.jdk.io;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.URL;

/**
 * IO练习：从网页读取内容
 *
 * @author wenhy
 * @date 2018/2/28
 */
public class DataStreamTest {

    public static void main(String[] args) {
        try{
            int num;
            byte[] buf = new byte[4096];
            URL url = new URL("http://cn.bing.com");
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            while((num=dataInputStream.read(buf))!=-1){
                System.out.write(buf,0,num);
            }
            System.out.println("\n\n=============================================================\n");
            // 装饰模式Stream
            DataInputStream dataInputStream2 = new DataInputStream(url.openStream());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(dataInputStream2);
            while(bufferedInputStream.read(buf)!=-1){
                System.out.println(new String(buf));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
