
###JDK不同版本的新特性###

#1.4
1.引入NIO

#1.5（Java语言重大升级）
1.引入JUC

2.引入泛型

3.引入注解

#1.6（JVM重大升级）
1.垃圾回收器G1

#1.7
1.钻石语法

2.try-with-resources语法糖

3.多个catch块用|连接

#1.8（Java语言重大升级）
1.Lambuda表达式
（闭包，允许我们将函数当成参数传递给某个方法，或者把代码本身当作数据处理）

2.接口中的默认方法和静态方法

3.方法引用
 无参构造器方法引用：类名::new 或者 Class<T>::new
 静态方法引用：Class::static_method （接受一个Class类型参数）
 类成员方法引用：Class::method （没有参数）
 实例成员方法引用：instance::method （接受一个instance对应类型的参数）
 
4.重复注解（同一个地方可以重复使用多次同一个注解，注解本身需要被@Repeatable修饰）

5.升级工具库实现
    HashMap实现修改
    ConCurrentHashMap实现修改
    新增Stream API(java.util.stream)
    
工具包：类依赖分析工具jdeps

JVM方面：使用Metaspace（JEP 122）代替持久代（PermGen space）

#1.9
1.Clean接口

#1.10
1.JVM类加载机制跟之前的双亲委派模型有了很大差别，趋向模块化设计

#1.11


###常用JVM工具###
字节码工具：asmtools.jar
查看字节码反汇编：javap -p -v XXX.class
