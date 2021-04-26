package top.hiccup.jdk.io.tel.serialize;

/**
 * 序列化器
 *
 * @author wenhy
 * @date 2019/1/24
 */
public interface Serializer {

    /**
     * 序列化算法标识
     */
    byte getSerializeAlgorithm();

    /**
     * 序列化
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     */
    <T> T deSerialize(Class<T> clazz, byte[] bytes);
}
