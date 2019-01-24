package top.hiccup.jdk.io.tel.protocol;

import lombok.Data;

/**
 * F
 *
 * @author wenhy
 * @date 2019/1/24
 */
@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public short getCommand() {
        return 1;
    }
}
