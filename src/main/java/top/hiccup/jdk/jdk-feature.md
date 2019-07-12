
# JDK不同版本的新特性

## JDK Beta（1995）
1995年发布alpha和beta Java公开版本，非常不稳定

## JDK1.0（1996）
Sun公司发布Java1.0，发布初期叫Oak，后改名为Java（JDK1.0基本上只支持Java语言基础特性）

## JDK1.1（1997）
1.引入内部类
2.引入JDBC
3.引入RMI

## J2SE 1.2（1998）
1.Java技术体系拆分为：J2SE、J2EE、J2ME
2.引入集合框架
3.引入JIT
4.引入EJB技术
5.引入Swing

## J2SE 1.3（2000）
1.引入Timer API
2.初次引入HotSpot VM

## J2SE 1.4（2002）（Java真正走向成熟的版本）
1.引入NIO
2.正则表达式
3.异常链
4.新增java.util.logging日志API

## J2SE 5.0（2004）（Java语言重大升级，版本号更名为5.0）
1.泛型
2.枚举
3.注解
4.自动装拆箱
5.静态导入（import static）
6.可变参数
7.JUC
8.For-Each循环

## Java SE 6（2006）（JVM重大升级）
1.引入垃圾回收器G1
2.优化锁与同步、垃圾收集、类加载等

## Java SE 7（2011）（SUN被Oracle收购，沉寂5年后由Oracle推出）
1.钻石语法（泛型实例化类型自动推断）
2.try-with-resources语法糖
3.多个catch块用|连接
4.switch中可以使用字符串
5.支持动态语言
6.64位JDK的指针压缩
7.数值可加下划线

## Java SE 8（2014）（Java语言重大升级，LTS）
1.Lambuda表达式（闭包，允许我们将函数当成参数传递给某个方法，或者把代码本身当作数据处理）
2.接口中的默认方法和静态方法
3.方法引用
    无参构造器方法引用：类名::new 或者 Class<T>::new
    静态方法引用：Class::static_method （接受一个Class类型参数）
    类成员方法引用：Class::method （没有参数）
    实例成员方法引用：instance::method （接受一个instance对应类型的参数）
4.重复注解（同一个地方可以重复使用多次同一个注解，注解本身需要被@Repeatable修饰）
5.升级工具库实现
    HashMap实现修改、ConCurrentHashMap实现修改
    新增Stream API(java.util.stream)
    新增Optional类以解决空指针问题
6.工具包：类依赖分析工具jdeps
7.JVM方面：使用Metaspace（JEP 122）代替方法区持久代（PermGen space）

## Java SE 9（2017）
1.JVM类加载机制跟之前的双亲委派模型有了很大差别，趋向模块化设计，即Jigsaw项目（参考OSGi？）
2.AOT编译，通过Graal VM实现
3.改善锁竞争机制
4.http2.0客户端
5.String类底层不再采用char数组存储
6.默认垃圾回收器改为G1

## Java SE 10（2018.3）
1.局部变量类型推断，var关键字

## Java SE 11（2018.9）（LTS）
1.动态类文件常量
2.Epsilon无操作垃圾收集器，常用来做测试
3.引入ZGC

## Java SE 12（2019.3）
1.实验性的低延迟垃圾收集器：Shenandoah

【参考】

[Java版本历史](https://zh.wikipedia.org/wiki/Java%E7%89%88%E6%9C%AC%E6%AD%B7%E5%8F%B2)

### 常用JDK工具
字节码工具：asmtools.jar
查看字节码反汇编：javap -p -v XXX.class
