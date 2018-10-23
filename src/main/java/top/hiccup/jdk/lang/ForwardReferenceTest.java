package top.hiccup.jdk.lang;

/**
 * 非法向前引用
 *
 * Java对于初始化过程中对成员变量的限制：成员变量temp如果满足如下的4点，就必须在使用前必须对该成员变量进行声明
 * 1.设定Test为直接包含该成员变量的类或者接口
 * 2.如果temp出现在Test的静态成员/非静态成员或者静态/非静态代码块中
 * 3.如果temp不是“一个赋值不等式的左值”
 * 4.通过简单名称来访问
 *
 * @author wenhy
 * @date 2018/10/23
 */
public class ForwardReferenceTest {

    static {
        temp = 1;
        // TODO 这里直接读temp会报错（非法的向前引用）
//        System.out.println(temp);
    }
    static int temp;

    {
        temp2 = 2;
        // TODO 这里直接读temp同样会报错（非法的向前引用）
//        System.out.println(temp2);
    }
    int temp2;

    {
        temp3 = 3;
        // 这里不存在向前引用的问题
        System.out.println(temp3);
    }
    static int temp3;
}
