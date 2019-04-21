
# JDK不同版本的新特性

## 1.0（1996）
（发布初期叫Oak，后改名为Java）
Sun公司发布Java1.0（JDK1.0，基本上只有Java语言基础特性）

## 1.1（1997）
1.内部类

## 1.2（1998）
1.Java技术体系拆分为：J2SE、J2EE、J2ME
2.EJB技术以及Swing等

## 1.3（2000）
1.Timer API

## 1.4（2002）（Java真正走向成熟的版本）
1.NIO
2.正则表达式
3.异常链

## 1.5（2004）（Java语言重大升级，版本号更名为JDK5）
1.泛型
2.枚举
3.注解
4.自动装拆箱
5.静态导入
6.可变参数
7.JUC
8.For-Each循环

## 1.6（2006）（JVM重大升级）
1.垃圾回收器G1
2.优化锁与同步、垃圾收集、类加载等

## 1.7（2011）（SUN被Oracle收购，沉寂5年后由Oracle推出）
1.钻石语法（泛型实例化类型自动推断）
2.try-with-resources语法糖
3.多个catch块用|连接
4.switch中可以使用字符串
5.数值可加下划线

## 1.8（2013）（Java语言重大升级）
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
JVM方面：使用Metaspace（JEP 122）代替方法区持久代（PermGen space）

## 1.9（2015）
1.改善锁竞争机制
2.http2.0客户端

## 1.10
1.JVM类加载机制跟之前的双亲委派模型有了很大差别，趋向模块化设计（要参考OSGi学习吗？）

## 1.11



# 常用JDK工具
字节码工具：asmtools.jar
查看字节码反汇编：javap -p -v XXX.class
