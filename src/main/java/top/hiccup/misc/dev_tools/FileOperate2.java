package top.hiccup.misc.dev_tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.hiccup.jdk.container.jdk7.HashMap;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * F
 *
 * @author wenhy
 * @date 2020/3/31
 */
public class FileOperate2 {

    public static void main(String[] args) {
        FileInputStream in = null;
        File fileOut = null;
        try {
            in = new FileInputStream("C:\\Users\\wenhaiyang3\\Desktop\\queue.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray array = JSON.parseArray(sb.toString());


            fileOut = new File("C:\\Users\\wenhaiyang3\\Desktop\\queue222.txt");
            final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)));

            Set<String> set = new HashSet(64);

            for (Object object : array) {
                JSONObject jsonObject = (JSONObject) object;
                String pin = jsonObject.getString("user_name");
                if (set.contains(pin)) {
                    System.out.println("重复pin：" + pin);
                } else {
                    set.add(pin);
                }
            }
            set.forEach((s) -> {
                String resultLine = s;
                resultLine += "\r\n";
                try {
                    bw.write(resultLine);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            bw.flush();

            System.out.println("转换完毕");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
