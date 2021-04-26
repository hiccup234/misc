package top.hiccup.jdk.io.tel.protocol;

/**
 * F
 *
 * @author wenhy
 * @date 2019/1/24
 */
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public short getCommand() {
        return 1;
    }
}
