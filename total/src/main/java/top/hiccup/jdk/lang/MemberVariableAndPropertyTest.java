package top.hiccup.jdk.lang;

/**
 * 成员变量与属性的区别：
 * 成员变量是从类结构定义的角度出发，是对象真实拥有的数据，
 * 而属性是从类或对象的使用者角度出发，其set和get方法可能是通过一个或多个变量的值运算得来
 * 如：某个美女“身高168cm”是成员变量，“颜值90分”是属性
 *
 * @author wenhy
 * @date 2018/1/17
 */
public class MemberVariableAndPropertyTest {

    /**
     * 成员变量，私有的对外不可见
     */
    private Integer id;
    private String name;

    /**
     * 此处mark为属性，对外公开可见可操作
     */
    public void setMark(String name) {
        this.name = name;
    }

}
