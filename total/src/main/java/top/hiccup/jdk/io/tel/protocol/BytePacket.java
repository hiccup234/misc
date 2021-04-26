package top.hiccup.jdk.io.tel.protocol;

/**
 * 通用数据包类
 *
 * @author wenhy
 * @date 2019/1/24
 */
public class BytePacket extends Packet {

    public BytePacket(byte[] data) {
        this.data = data;
        super.setSerialize((byte) 1);
    }

    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
