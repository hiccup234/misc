package top.hiccup.schema.design.proxy;

/**
 * 定义BusiService接口
 *
 * @author wenhy
 * @date 2018/1/14
 */
public interface IBusiService {

    String getName();

    String innerInvoke();

    default String defaultMethod() {
        System.out.println("interface defaultMethod");
        return getName();
    }
}
