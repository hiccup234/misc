package top.hiccup.schema.design.factory.ioc;

public class IoCTest {
    public static void main(String[] args)
            throws Exception {
        //创建IoC容器
        ApplicationContext ctx = new YeekuXmlApplicationContext("bean.xml");
        //从IoC容器中取出bean
        System.out.println(ctx.getBean("now"));
    }
}
