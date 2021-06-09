package top.hiccup.schema.design.factory.ioc;

public interface MyBeanFactory {

    Object getBean(String beanName) throws Exception;
}
