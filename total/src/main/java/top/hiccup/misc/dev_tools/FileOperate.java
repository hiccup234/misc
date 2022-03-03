package top.hiccup.misc.dev_tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

/**
 * 文件操作
 *
 * @author wenhy
 * @date 2020/3/31
 */
public class FileOperate {

    public static void main(String[] args) {
        FileInputStream in = null;
        BufferedWriter bw = null;
        File fileOut = null;
        try {
            in = new FileInputStream("C:\\Users\\wenhaiyang3\\Desktop\\flowww.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray array = JSON.parseArray(sb.toString());
            String table = "INSERT INTO `jtalk_flow_info` (`flow_type`, `flow_id`, `title`, `speech_craft`, `remark`," +
                    " `first_choice_text`,`second_choice_text`,`third_choice_text`,`forth_choice_text`, `first_flow_id`," +
                    "`second_flow_id`,`third_flow_id`,`forth_flow_id`, `extension`,`created`,`created_date`) VALUES (";

            fileOut = new File("C:\\Users\\wenhaiyang3\\Desktop\\flow22.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)));

            for (Object object : array) {
                JSONObject jsonObject = (JSONObject) object;
                String resultLine = table;
                resultLine += "'" + jsonObject.getString("flow_type") + "', ";
                resultLine += jsonObject.getString("flow_id");
                resultLine += jsonObject.getString("title");
                resultLine += jsonObject.getString("speech_craft");
                resultLine += jsonObject.getString("remark");
                resultLine += jsonObject.getString("first_choice_text");
                resultLine += jsonObject.getString("second_choice_text");
                resultLine += jsonObject.getString("third_choice_text");
                resultLine += jsonObject.getString("forth_choice_text");
                resultLine += jsonObject.getString("first_flow_id");
                resultLine += jsonObject.getString("second_flow_id");
                resultLine += jsonObject.getString("third_flow_id");
                resultLine += jsonObject.getString("forth_flow_id");
                resultLine += jsonObject.getString("extension");
                resultLine += jsonObject.getString("created");
                resultLine += jsonObject.getString("created_date");
                resultLine += "\"); \n";
                bw.write(resultLine);
            }
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
