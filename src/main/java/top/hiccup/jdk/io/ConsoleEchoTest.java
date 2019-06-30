package top.hiccup.jdk.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * IO练习：从控制台获取输入再回显到控制台
 *
 * @author wenhy
 * @date 2017/12/25
 */
public class ConsoleEchoTest {

    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String lineStr = br.readLine();
                if("Q".equals(lineStr.toUpperCase())) {
                    break;
                }
                System.out.println(lineStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != br) {
                try {
                    // 一定要记得关闭流
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}