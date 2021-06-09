package top.hiccup.schema.design.factory.ioc;

public class IoCTest {

    public static void main(String[] args) throws Exception {
        MyBeanFactory ctx = new MyXmlBeanFactory("schema\\factory\\ioc\\bean.xml");
        System.out.println(ctx.getBean("now"));
        System.out.println(ctx.getBean("user"));
        System.out.println(ctx.getBean("now"));
    }

}
