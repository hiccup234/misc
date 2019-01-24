package top.hiccup.jdk.io.tel.protocol;

import lombok.Data;

/**
 * 消息通信数据报
 *
 * @author wenhy
 * @date 2019/1/25
 */
@Data
public class MsgPacket extends Packet {

    private String msg;

    public MsgPacket() {
       this(null);
    }

    public MsgPacket(String msg) {
        this.msg = msg;
        super.setSerialize(Serialize.MSG.getValue());
    }
}
