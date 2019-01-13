package top.hiccup.jdk.io;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取时间工具类
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class DateUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String now() {
        return sdf.format(new Date());
    }
}
