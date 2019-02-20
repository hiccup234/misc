package top.hiccup.jdk.vm;

/**
 * JDK常用工具（bin目录下）
 *
 * 【javac】  编译源代码为class文件
 * 【java】   运行Java程序
 * 【javap】  解析和反编译class文件
 *
 * 【jps】    查看正在运行的虚拟机（VM）进程ID（LVMID：Local Virtual Machine Identifier），一般情况下LVMID跟操作系统的进程ID是一样的
 * 【jstat】  监视虚拟机进程的各种运行状态（jstat -options 来查看参数列表）
 *              jstat -class 1099           查看进程号为1099的虚拟机实例加载了多少类
 *              jstat -compiler 1099        查看编译统计
 *              jstat -gc 1099              查看gc情况
 *                      S0C：第一个幸存区的大小
 *                      S1C：第二个幸存区的大小
 *                      S0U：第一个幸存区的使用大小
 *                      S1U：第二个幸存区的使用大小
 *                      EC：伊甸园区的大小
 *                      EU：伊甸园区的使用大小
 *                      OC：老年代大小
 *                      OU：老年代使用大小
 *                      MC：方法区大小（元数据空间）
 *                      MU：方法区使用大小
 *                      CCSC:压缩类空间大小
 *                      CCSU:压缩类空间使用大小
 *                      YGC：年轻代垃圾回收次数
 *                      YGCT：年轻代垃圾回收消耗时间
 *                      FGC：老年代垃圾回收次数
 *                      FGCT：老年代垃圾回收消耗时间
 *                      GCT：垃圾回收消耗总时间
 *              jstat -gccapacity 1099      查看对内存统计
 *              jstat -gcutil 1099 3000     总结垃圾回收统计，每3秒钟打印一次gc状态
 *                      S0：幸存1区当前使用比例
 *                      S1：幸存2区当前使用比例
 *                      E：伊甸园区使用比例
 *                      O：老年代使用比例
 *                      M：元数据区使用比例
 *                      CCS：压缩使用比例
 *                      YGC：年轻代垃圾回收次数
 *                      FGC：老年代垃圾回收次数
 *                      FGCT：老年代垃圾回收消耗时间
 *                      GCT：垃圾回收消耗总时间
 *
 * ##为了找出导致频繁Full GC的原因，一般有两种方法
 *      1、把堆dump下来再用MAT等工具进行分析，但dump堆要花较长的时间，并且文件巨大，再从服务器上拖回本地导入工具，这个过程有些折腾，不到万不得已最好别这么干。
 *      2、更轻量级的在线分析，使用“Java内存影像工具：jmap”生成堆转储快照（一般称为headdump或dump文件）。
 *
 * 【jmap】 主要用于查看内存情况和生成dump文件（内存快照）  jmap -dump:format=b,file=test.bin Vmid
 *
 * 【jstack】 主要用于查看线程运行状态（顾名思义：stack一般跟VM的线程栈有关系），分析线程阻塞、死锁等问题。  jstack 1591 > jstack.log
 *
 *      查看最耗CPU的Java进程ID： ps -eo user,pid,ppid,tid,time,%cpu,cmd | grep java | sort -k6 -nr
 *
 * 【jhat】 内存dump文件分析工具，同mat， -J-Xmx4000M test.bin
 *
 * 【jconsole】
 *
 * 【jinfo】 查看VM进程的配置信息
 *
 * @author wenhy
 * @date 2019/1/16
 */
public class DevpKitTest {

    /**
     * 虚拟机性能监控与故障处理工具
     *  jps, jstack, jmap, jstat, jconsole, jinfo, jhat, javap, btrace, TProfiler, Arthas, MAT
     */

}
