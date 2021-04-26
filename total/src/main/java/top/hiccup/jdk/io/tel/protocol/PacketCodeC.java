package top.hiccup.jdk.io.tel.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import top.hiccup.jdk.io.tel.serialize.SerializeHelper;

/**
 * 将序列化后的二进制数据封包
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class PacketCodeC {

    /**
     * 定义魔数：4字节，类似于class文件
     */
    public static final int MAGIC_NUMBER = 0xcafeaabb;

    private PacketCodeC() {}

    /**
     * 对给出的数据包进行编码
     *
     * @param packet
     * @return
     */
    public static ByteBuf encode(Packet packet) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byte serialize = packet.getSerialize();
        byteBuf.writeByte(serialize);
        byteBuf.writeShort(packet.getCommand());
        byte[] bytes = SerializeHelper.serialize(serialize, packet);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public static Packet decode(ByteBuf byteBuf) {
        // 直接跳过 MAGIC_NUMBER
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 序列化算法标识
        byte serialize = byteBuf.readByte();
        // 读取指令
        short command = byteBuf.readShort();
        // 数据包长度
        int length = byteBuf.readInt();
        byte[] data = new byte[length];
        byteBuf.readBytes(data);
//        Class<? extends Packet> requestType = getRequestType(command);
//        Serializer serializer = getSerializer(serializeAlgorithm);
//
//        if (requestType != null && serializer != null) {
//            return serializer.deserialize(requestType, bytes);
//        }
        if (serialize == (byte)2) {
            Packet packet = new MsgPacket(new String(data));
            return packet;
        }
        return SerializeHelper.deSerialize(Packet.class, data);
    }
}
