package top.hiccup.misc.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 原生JDBC连接创建、执行步骤
 *
 * @author wenhy
 * @date 2018/11/18
 */
public class JdbcTest {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 1.加载数据库驱动类
            Class.forName("com.mysql.jdbc.Driver");
            // 2.创建连接
            conn = DriverManager.getConnection("jdbc:mysql://47.106.155.243:3306/redcap?useUnicode=true&characterEncoding=utf8", "root", "123456");
            // 3.创建预编译语句
            PreparedStatement ps = conn.prepareStatement("select * from user where id = ?");
            ps.setInt(1, 1);
            // 4.执行语句
            ResultSet rs = ps.executeQuery();
            // 5.处理结果
            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getString("phone"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
