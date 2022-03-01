package top.hiccup.schema.principle;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 开闭原则（对扩展开放，对修改关闭）
 *
 * @author wenhy
 * @date 2018/8/18
 */
public class OpenClosePrinciple {

    private static Map<Class<?>, ServiceProvider> providers = new HashMap<>();

    static {
        providers.put(OrdinaryUser.class, new OrdinaryUserServiceProvider());
        providers.put(VIPUser.class, new VIPUserServiceProvider());
        providers.put(VVIPUser.class, new VVIPUserServiceProvider());
    }

    /**
     * 对扩展不友好的写法：直接用if else判断
     *
     * @param user
     * @param <T>
     */
    public <T extends User> void service(T user) {
        if (user instanceof OrdinaryUser) {
            System.out.println("欢迎光临：" + user.getName());
        } else if (user instanceof VVIPUser) {
            System.out.println("欢迎光``光``光``临``：" + user.getName());
        } else if (user instanceof VIPUser) {
            System.out.println("欢迎光``临：" + user.getName());
        }
    }

    /**
     * 采用开闭原则实现
     *
     * @param user
     * @param <T>
     */
    public <T extends User> void service2(T user) {
        providers.get(user.getClass()).doService(user);
    }

    @Test
    public void test() {
        User user = new OrdinaryUser();
        service(user);
        user = new VIPUser();
        service(user);
        user = new VVIPUser();
        service(user);
        System.out.println("=======================================================");
        User user2 = new OrdinaryUser();
        service2(user2);
        user2 = new VIPUser();
        service2(user2);
        user2 = new VVIPUser();
        service2(user2);
    }

    interface User {
        String getName();
    }

    interface ServiceProvider {
        <T extends User> void doService(T user);
    }

    static class OrdinaryUserServiceProvider implements ServiceProvider {
        @Override
        public <T extends User> void doService(T user) {
            System.out.println("欢迎光临：" + user.getName());
        }
    }

    static class VIPUserServiceProvider implements ServiceProvider {
        @Override
        public <T extends User> void doService(T user) {
            System.out.println("欢迎光``临：" + user.getName());
        }
    }

    static class VVIPUserServiceProvider implements ServiceProvider {
        @Override
        public <T extends User> void doService(T user) {
            System.out.println("欢迎光``光``光``临``：" + user.getName());
        }
    }

    class OrdinaryUser implements User {
        @Override
        public String getName() {
            return "xxx";
        }
    }

    class VIPUser implements User {
        @Override
        public String getName() {
            return "尊贵的xxx";
        }
    }

    class VVIPUser extends VIPUser {
    }
}
