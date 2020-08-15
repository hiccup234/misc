package top.hiccup.schema.design.proxy.staticc;

import org.junit.Test;

/**
 * 代理一般分为：静态代理 和 动态代理
 *
 * 静态代理其实就是通过代码耦合起来，一旦原接口增加方法，目标对象与代理对象都要修改
 *
 * @author wenhy
 * @date 2019/2/16
 */
public class StaticProxyTest {

    private class User {};

    public interface UserIntf {
        boolean add(User user);
    }

    public class UserIntfImpl implements UserIntf {
        @Override
        public boolean add(User user) {
            System.out.println("正在保存用户信息..");
            return true;
        }
    }

    public class StaticProxy implements UserIntf {
        private UserIntf userIntf;
        public StaticProxy(UserIntf userIntf) {
            this.userIntf = userIntf;
        }
        @Override
        public boolean add(User user) {
            System.out.println("静态代理--保存用户信息前..");
            boolean result = userIntf.add(user);
            System.out.println("静态代理--保存用户信息后..");
            return result;
        }
    }

    @Test
    public void test() {
        UserIntf userIntf = new UserIntfImpl();
        StaticProxy proxy = new StaticProxy(userIntf);
        proxy.add(new User());
    }
}
