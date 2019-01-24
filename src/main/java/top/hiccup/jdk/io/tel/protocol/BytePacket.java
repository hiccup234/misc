package top.hiccup.jdk.io.tel.protocol;

import lombok.Data;

/**
 * 通用数据包类
 *
 * @author wenhy
 * @date 2019/1/24
 */
@Data
public class BytePacket extends Packet {

    private byte[] data;

    public BytePacket(byte[] data) {
        this.data = data;
        super.setSerialize((byte) 1);
    }
}
