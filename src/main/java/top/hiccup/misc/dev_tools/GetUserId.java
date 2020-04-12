package top.hiccup.misc.dev_tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 获取userId
 *
 * @author wenhy
 * @date 2020/3/31
 */
public class GetUserId {

    private static void switchFile(String fileName) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject txt = JSONObject.parseObject(line);
                JSONArray array = txt.getJSONObject("resultInfo").getJSONArray("data");
                for (Object object : array) {
                    JSONObject jsonObject = (JSONObject) object;
                    String erp = jsonObject.getString("user_name");
                    String userId = jsonObject.getString("user_id");
                    System.out.printf("%-20s    %20s\n", erp, userId);
                }
            }
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

    public static void main(String[] args) {
        switchFile("C:\\Users\\wenhaiyang3\\Desktop\\jtk_waiter\\jtk_waiter");
    }
}
