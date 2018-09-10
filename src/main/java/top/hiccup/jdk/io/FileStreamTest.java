package top.hiccup.jdk.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wenhy on 2017/12/25.
 */
public class FileStreamTest {

    /**
     *  文件输入流示例
     */
    public void fileOutStreamTest() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File fileOut = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            fileOut = new File("enjoy.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)));
            while (true) {
                String lineStr = br.readLine();
                bw.write(lineStr);
                if("Q".equals(lineStr.toUpperCase())) {
                    bw.flush();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) throws Exception {

        //文件输入流
//        FileStreamTest fileStreamTest = new FileStreamTest();
//        fileStreamTest.fileOutStreamTest();

        HashMap hashMap = new HashMap();
        Thread.sleep(1000);
        Lock lock = new ReentrantLock();
        switch (4) {
            default:
                System.out.println(4);
            case 1:
                System.out.println(1);
            case 2:
                System.out.println(2);
            case 3:
                System.out.println(3);
        }


        try{
            throw new Exception("1");
        }catch (IOException e){
            throw new Exception("2");
        }catch (Exception e) {
            throw new Exception("3");
        }finally {
            throw new Exception("4");
        }

    }

}
