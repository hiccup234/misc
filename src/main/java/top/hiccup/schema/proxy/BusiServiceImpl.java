package top.hiccup.schema.proxy;

import top.hiccup.schema.proxy.IBusiService;

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
    public String getStr() {
        return new String("abc");
    }

}
