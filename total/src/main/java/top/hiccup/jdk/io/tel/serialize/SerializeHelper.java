package top.hiccup.jdk.io.tel.serialize;

import com.alibaba.fastjson.JSONObject;
import top.hiccup.jdk.io.tel.protocol.BytePacket;
import top.hiccup.jdk.io.tel.protocol.MsgPacket;

/**
 * 序列化工具类
 *
 * @author wenhy
 * @date 2019/1/24
 */
public class SerializeHelper {

    public static byte[] serialize(byte serialize, Object object) {
        if (serialize == (byte) 1) {
           return ((BytePacket)object).getData();
        } else if (serialize == (byte) 2) {
            return ((MsgPacket)object).getMsg().getBytes();
        }
        return JSONObject.toJSONString(object).getBytes();
    }

    public static <T> T deSerialize(Class<T> clazz, byte[] bytes) {
        return (T) new String(bytes);
    }
}
