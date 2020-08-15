package top.hiccup.schema.design.proxy.dynamic;

/**
 * 实现了接口的Service类
 *
 * @author wenhy
 * @date 2018/1/14
 */
public class BusiServiceImpl implements IBusiService {

    public BusiServiceImpl() {

    }

    @Override
    public String getName() {
        System.out.println("目标方法：getName");
        this.innerInvoke();
        return new String("abc");
    }

    @Override
    public String innerInvoke() {
        System.out.println("目标方法：innerInvoke");
        return null;
    }

}
