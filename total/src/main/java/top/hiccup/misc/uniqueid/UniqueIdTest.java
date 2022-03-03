package top.hiccup.misc.uniqueid;

import java.util.UUID;

/**
 * 全局唯一ID：
 * <p>
 * 【几个要求】
 * 1、全局唯一性：确保生成的ID是全局唯一的。
 * 2、有序递增性：确保生成的ID是对于某个用户或者业务是按一定的顺序有序递增的。
 * 3、高可用性：确保任何时候都能正确的生成ID。
 * <p>
 * <p>
 * 1、UUID：实现简单但无序，不能保证递增，采用字符存储，查询传输慢（不适合做MySQL的主键）
 * <p>
 * 2、snowflake算法：twitter分布式主键ID生成算法，无序且强依赖时钟，多台服务器时钟要同步
 * 1bit+41bit+10bit+12bit=64bit
 * 固定0（为1的话则生成的ID都是负数了）+毫秒数（2^41-1毫秒，约69年）+机器码（数据中心+机器ID）+流水号
 * <p>
 * 3、MySql自增主键：假设有100台MySql数据库，则自增步长设置为100，可扩展性非常差
 * 如库A的id=100，200，300 库B的id=101，201，301
 * <p>
 * 4、通过Redis的incr命令来生成ID，性能较高（每秒10W的写入）
 * <p>
 * 5、其他框架如：美团的Leaf、百度的UidGenerator等
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class UniqueIdTest {

    public static void test1() {
        System.out.println(UUID.randomUUID().toString());
    }

    public static void test2() {
        SnowFlakeGenerator generator = new SnowFlakeGenerator.Factory().create(234001L, 1L);
        for (int i = 0; i < 10000; i++) {
            System.out.println(generator.nextId());
        }
    }

    public static void main(String[] args) {
        test2();
    }
}