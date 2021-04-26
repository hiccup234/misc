package top.hiccup.schema.design.factory.ioc;

public interface ApplicationContext {

    Object getBean(String beanName) throws Exception;
}
