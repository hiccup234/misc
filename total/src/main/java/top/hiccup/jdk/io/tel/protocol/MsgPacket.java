package top.hiccup.jdk.io.tel.protocol;

/**
 * 消息通信数据报
 *
 * @author wenhy
 * @date 2019/1/25
 */
public class MsgPacket extends Packet {

    public MsgPacket() {
        this(null);
    }

    public MsgPacket(String msg) {
        this.msg = msg;
        super.setSerialize(Serialize.MSG.getValue());
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
