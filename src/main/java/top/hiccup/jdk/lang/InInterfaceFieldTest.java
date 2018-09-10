package top.hiccup.jdk.lang;

/**
 * 接口中的域隐式地是static和final的
 * Java 5 enum出来前常被当做定义常量的地方：@org.springframework.transaction.TransactionDefinition
 *
 * @author wenhy
 * @date 2018/5/26
 */
public class InInterfaceFieldTest {

    public static void main(String[] args) {
        System.out.println(FieldInterface.NAME);
        // 不能对final域进行赋值
//        FieldInterface.NAME = new String("haha");
    }
}

interface FieldInterface {

    /**
     * 要求必须初始化
     */
    String NAME = "Bob";
}