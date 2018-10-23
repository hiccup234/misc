package top.hiccup.schema.principle;

import java.util.HashMap;
import java.util.Map;

/**
 * 开闭原则（对扩展开放，对修改关闭）
 *
 * @author wenhy
 * @date 2018/8/18
 */
public class OpenClosePrinciple {

    interface User {}

    class OrdinaryUser implements User {}

    class VIPUser implements User {}

    class VVIPUser extends VIPUser {}

    /**
     * 对扩展不友好的写法：直接用if else判断
     * @param user
     * @param <T>
     */
    public <T extends User> void service(T user) {
        if(user instanceof OrdinaryUser) {
            // 普通用户...
            System.out.println(user.getClass().getName());
        } else if(user instanceof VIPUser) {
            // VIP用户...
            System.out.println(user.getClass().getName());
        } else if(user instanceof VVIPUser) {
            // VVIP用户...
            System.out.println(user.getClass().getName());
        }
        // ...
    }

    /**
     * 采用开闭原则实现
     * @param user
     * @param <T>
     */
    public <T extends User> void service2(T user) {
        providers.get(user.getClass()).doService(user);
    }
    interface ServiceProvider {
        <T extends User> void doService(T user);
    }
    static class OrdinaryUserServiceProvider implements ServiceProvider {
        @Override
        public <T extends User> void doService(T user) {
            System.out.println(user.getClass().getName());
        }
    }
    static class VIPUserServiceProvider implements ServiceProvider {
        @Override
        public <T extends User> void doService(T user) {
            System.out.println(user.getClass().getName());
        }
    }
    static class VVIPUserServiceProvider implements ServiceProvider {
        @Override
        public <T extends User> void doService(T user) {
            System.out.println(user.getClass().getName());
        }
    }
    private static Map<Class<?>, ServiceProvider> providers = new HashMap<>();
    static {
        providers.put(OrdinaryUser.class, new OrdinaryUserServiceProvider());
        providers.put(VIPUser.class, new VIPUserServiceProvider());
        providers.put(VVIPUser.class, new VVIPUserServiceProvider());
    }

    void test() {
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

    public static void main(String[] args) {
        OpenClosePrinciple main = new OpenClosePrinciple();
        main.test();
    }

}
