package top.hiccup.jdk.io.tel.protocol;

/**
 * 自定义通信协议数据报
 *
 *     魔数  +   版本号  +   序列化算法   +   操作指令    +   数据长度    +   数据内容
 *     4字节     1字节       1字节           2字节          4字节          不定长
 *
 * @author wenhy
 * @date 2019/1/13
 */
public abstract class Packet {

    /**
     * 协议版本号：1字节（默认为 1）
     */
    private byte version = 1;
    /**
     * 媒介类型+序列化算法
     */
    private byte serialize;
    /**
     * 通信指令：2字节
     */
    private short command;
    /**
     * 数据长度
     */
    private int contentLength;

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getSerialize() {
        return serialize;
    }

    public void setSerialize(byte serialize) {
        this.serialize = serialize;
    }

    public short getCommand() {
        return command;
    }

    public void setCommand(short command) {
        this.command = command;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }
}

enum Serialize {    BYTES((byte)1, "字节数组"),    MSG((byte)2, "字符消息"),
    REQUEST((byte)3, "请求对象");

    private byte serialize;
    private String desc;

    Serialize(byte serialize, String desc) {
        this.serialize = serialize;
        this.desc = desc;
    }

    public byte getValue() {
        return serialize;
    }

    public String getDesc() {
        return desc;
    }
}