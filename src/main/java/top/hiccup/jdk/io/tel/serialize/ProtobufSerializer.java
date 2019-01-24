package top.hiccup.jdk.io.tel.serialize;

/**
 * protobuf序列化器
 *
 * @author wenhy
 * @date 2019/1/24
 */
public class ProtobufSerializer implements Serializer{
    @Override
    public byte getSerializeAlgorithm() {
        return 0;
    }

    @Override
    public byte[] serialize(Object object) {
        return new byte[0];
    }

    @Override
    public <T> T deSerialize(Class<T> clazz, byte[] bytes) {
        return null;
    }
}
