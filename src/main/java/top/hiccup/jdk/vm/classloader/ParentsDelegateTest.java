package top.hiccup.jdk.vm.classloader;

import java.io.IOException;

/**
 * 双亲委派模型（旨在解决类的重复加载问题，以及安全性问题）：
 *
 * 1.Bootrap ClassLoader(启动ClassLoader，用C++实现)，对应%JAVA_HOME%/lib下的jar文件包含rt.jar，
 *      或者用启动参数-Xbootclasspath设定或追加启动目录
 *      # 指定新的 bootclasspath，替换 java.* 包的内部实现
 *          java -Xbootclasspath:<your_boot_classpath> your_App
 *
 *      # a 意味着 append，将指定目录添加到 bootclasspath 后面
 *          java -Xbootclasspath/a:<your_dir> your_App
 *
 *      # p 意味着 prepend，将指定目录添加到 bootclasspath 前面
 *          java -Xbootclasspath/p:<your_dir> your_App
 *
 * 2.Extension ClassLoader(extends ClassLoader)，对应%JAVA_HOME%/lib/ext/*.jar和系统环境变量java.ext.dirs指定目录所有类库
 *      # 指定新的 java.ext.dirs 来覆盖原来的jre/lib/ext路径
 *          java -Djava.ext.dirs=your_ext_dir HelloWorld
 *
 * 3.App ClassLoader(又叫系统加载器 system ClassLoader)，对应classpath
 *      # 指定 java.system.class.loader 来设置默认的系统加载器
 *          java -Djava.system.class.loader=com.yourcorp.YourClassLoader HelloWorld
 *
 * 4.Custom ClassLoader(自定义ClassLoader)，对应自定义路径，可以实现进程内的隔离，不同模块拆用不同的类自定义类加载器，相互之间不受影响，参考Tomcat
 *
 * ======================================================================================================================
 * Q：Java启动慢的解决办法？
 * 1、AOT，直接编译成机器码而不是字节码，牺牲了跨平台的特性但是降低了解释和编译的开销
 * 2、AppCDS（Application Class-Data Sharing），JDK1.5中引进
 *      大致原理：JVM 将类信息加载、解析成为元数据，并根据是否需要修改，将其分类为 Read-Only 部分和 Read-Write 部分。
 *              然后，将这些元数据直接存储在文件系统中，作为所谓的 Shared Archive。
 *      命令参数：Java -Xshare:dump -XX:+UseAppCDS -XX:SharedArchiveFile=<jsa>  \
 *                   -XX:SharedClassListFile=<classlist> -XX:SharedArchiveConfigFile=<config_file>
 *      启动应用：在应用程序启动时，指定归档文件，并开启 AppCDS。
 *              Java -Xshare:on -XX:+UseAppCDS -XX:SharedArchiveFile=<jsa> yourApp
 *      通过上面的命令，JVM 会通过内存映射技术，直接映射到相应的地址空间，免除了类加载、解析等各种开销。
 *      对于传统的 Java EE 应用，启动速度一般可以提高 20%~30% 以上
 * ======================================================================================================================
 *
 * 【jar hell问题】jar包冲突问题
 * 1、可以通过使用Maven来做项目管理，Maven Helper 和 mvn dependency:tree 配合来找出冲突jar包并排除
 *
 * @author wenhy
 * @date 2018/10/23
 */
public class ParentsDelegateTest {

    public static void main(String[] args) {
        // 找出某个类是否被重复加载存在jar hell问题
//        try {
//            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//            String resourceName = "net/sf/cglib/proxy/MethodInterceptor.class";
//            Enumeration<URL> urls = classLoader.getResources(resourceName);
//            while(urls.hasMoreElements()){
//                System.out.println(urls.nextElement());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        ClassLoader classLoader = ParentsDelegateTest.class.getClassLoader();
        while (null != classLoader) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }
        System.out.println(classLoader);
    }
}
