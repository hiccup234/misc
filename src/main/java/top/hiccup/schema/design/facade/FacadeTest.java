package top.hiccup.schema.design.facade;

/**
 * 门面模式：组装和整合多个职能部门功能，然后对外提供一个简单的API
 *
 * 在什么情况下使用门面模式？
 * 1、为一个复杂子系统提供一个简单接口
 * 2、提高子系统的独立性
 * 3、在层次化结构中，可以使用Facade模式定义系统中每一层的入口
 *
 * 使用案例：
 * 1、Java EE应用中的分层设计，Controller >> Service >> DAO, 其中Service就是封装了Dao层的一个门面
 *
 * @author wenhy
 * @date 2020/8/16
 */
public class FacadeTest {
}
