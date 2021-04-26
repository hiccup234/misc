package top.hiccup.jdk.lang;

/**
 * 多态性测试：属性域的访问的静态绑定，以及非final实例方法的动态绑定
 *
 * @author wenhy
 * @date 2018/1/26
 */
public class PolymorphismTest {

    public static void main(String[] args) {
        Super sup = new Sub();
        // 这里的sup.field属于静态绑定，编译时已经确定了目标字段
        System.out.println(sup.field+" "+sup.getField());

        Sub sub = new Sub();
        System.out.println(sub.field+" "+sub.getField()+" "+sub.getSuperField());
    }
}

class Super {
    public int field = 0;
    public int getField() {
        return field;
    }
}

class Sub extends Super {
    public int field = 1;
    @Override
    public int getField() {
        return field;
    }
    public int getSuperField() {
        return super.field;
    }
}