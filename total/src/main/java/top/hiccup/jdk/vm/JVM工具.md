
## JDK常用工具
    一般放在JDK的bin目录下，这些工具其实都是对jmx(jdk/lib/tools.jar)的一层薄薄的封装，所以一般都是16KB大小
    
    【javac】 编译源代码为class文件，一般情况下要指定字符集：javac -encoding utf-8 Test.java
    【java】  创建一个JVM进程，运行Java程序，如果main方法入口类带有package的包名记得cd到对应包的上一级目录，不然会报错：找不到或无法加载主类 Test
    【javap】 解析和反编译class文件，可以查看.java源文件编译后的.class中的常量池、字节码等

    【jps】   查看正在运行的虚拟机（VM）进程ID（LVMID：Local Virtual Machine Identifier），一般情况下LVMID跟操作系统的进程ID是一样的：jps -mlvV
    【jinfo】 查看VM版本，VM进程配置信息（Java System Properties），启动参数（VM Flags）：jinfo 1099
    【jstat】 监视虚拟机进程的各种运行状态（jstat -options 来查看参数列表）
             jstat -class 1099           查看进程号为1099的虚拟机实例加载了多少类
             jstat -compiler 1099        查看编译统计
             jstat -gc 1099              查看gc情况（常用）
                     S0C：第一个幸存区的大小
                     S1C：第二个幸存区的大小
                     S0U：第一个幸存区的使用大小
                     S1U：第二个幸存区的使用大小
                     EC：伊甸园区的大小
                     EU：伊甸园区的使用大小
                     OC：老年代大小
                     OU：老年代使用大小
                     MC：方法区大小（元数据空间）
                     MU：方法区使用大小
                     CCSC:压缩类空间大小
                     CCSU:压缩类空间使用大小
                     YGC：年轻代垃圾回收次数
                     YGCT：年轻代垃圾回收消耗时间
                     FGC：老年代垃圾回收次数
                     FGCT：老年代垃圾回收消耗时间
                     GCT：垃圾回收消耗总时间
             jstat -gccapacity 1099      查看对内存统计
             jstat -gcutil 1099 3000     总结垃圾回收统计，每3秒钟打印一次gc状态（常用）
                     S0：幸存1区当前使用比例
                     S1：幸存2区当前使用比例
                     E：伊甸园区使用比例
                     O：老年代使用比例
                     M：元数据区使用比例
                     CCS：压缩使用比例
                     YGC：年轻代垃圾回收次数
                     FGC：老年代垃圾回收次数
                     FGCT：老年代垃圾回收消耗时间
                     GCT：垃圾回收消耗总时间
    【jmap】 主要用于查看内存情况和生成dump文件（内存快照），查看堆内存使用情况：jmap -heap 1099
            生成堆快照，一般要加上live参数：jmap -dump:live,format=b,file=test.bin 1099
    【jstack】 主要用于查看线程运行状态（顾名思义：stack一般跟VM的线程栈有关系），分析线程阻塞、死锁等问题：jstack 1099 > jstack1099.log
              查看最耗CPU的Java进程ID：ps -eo user,pid,ppid,tid,time,%cpu,cmd | grep java | sort -k6 -nr
    【jhat】 内存dump文件分析工具，同mat：-J-Xmx4000M test.bin
    
    【jconsole】 对上述工具的整合后的可视化工具
    【jclasslib】 可以查看class文件信息


#### 虚拟机性能监控与故障处理工具
    jps, jstack, jmap, jstat, jconsole, jinfo, jhat, javap, btrace, TProfiler, Arthas, MAT

    获取threaddump线程栈快照文件： jstack LVMID  或者在Linux环境下用kill -3 pid(同LVMID)吓唬JVM生成dump文件
     
    Linux环境下如何查找哪个线程使用CPU最长？   top -H -p pid
    
    JProfiler, YourKit 监控CPU消耗的工具，可以列出整个请求中每个函数的CPU执行时间
 

    通过 java -XX:+PrintCommandLineFlags -version 来得到当前环境下JDK建议的启动参数
    本机测试：
    -XX:InitialHeapSize=266502400 -XX:MaxHeapSize=4264038400 -XX:+PrintCommandLineFlags
    -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC
    java version "1.8.0_162"
    

    生成内存中Class对象的Dump文件（class字节码）： java -classpath "%JAVA_HOME%/lib/sa-jdi.jar" sun.jvm.hotspot.HSD

### JVM虚拟机人物

    R大（知乎账号：你假笨）  
    
    江南白衣（唯品会资深架构师）
    
    占小狼（美团基础架构部技术专家）
    
    杨晓峰（Oracle首席工程师）
    
    郑雨迪（Oracle高级研究员）
    
    Cliff Click
    
    Aleksey Shipilev
    
    Nitsan Wakart
    
    Java Virtual Machine Language Submit
    
    Oracle Code One(JavaOne)
    
    