package top.hiccup.jdk.io.tel.serialize;

import com.alibaba.fastjson.JSON;

/**
 * F
 *
 * @author wenhy
 * @date 2019/1/24
 */
public class JSONSerializer implements Serializer{

    @Override
    public byte getSerializeAlgorithm() {
        return 1;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deSerialize(Class<T> clazz, byte[] bytes) {
        return null;
    }
}
