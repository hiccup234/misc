package top.hiccup.schema.proxy.cglib;

/**
 * Created by wenhy on 2018/1/14.
 */
public class BusiServiceImpl implements IBusiService {

    public BusiServiceImpl() {

    }

    public String getStr() {
        return new String("abc");
    }

}
