package top.hiccup.jdk.io;

import java.io.DataInputStream;
import java.net.URL;

/**
 * 从读取网页读取内容
 *
 * @author wenhy
 * @date 2018/2/28
 */
public class DataStreamTest {

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

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
