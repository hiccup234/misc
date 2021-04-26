package top.hiccup.jdk.vm.jmm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.堆区内存溢出测试
 * @VM args: -Xms64m -Xmx64m -XX:+PrintGCDetails
 *
 * 2.OOM时导出dump文件
 * @VM args: -Xms64m -Xmx64m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=C:\Users\haiyang.wen\Desktop\heap_oom.dump
 *
 * 3.OOM时执行脚本
 * @VM args: -Xms64m -Xmx64m -XX:+PrintGCDetails -XX:OnOutOfMemoryError=C:\Users\haiyang.wen\Desktop\dump.bat
 * @VM args: -Xms64m -Xmx64m -XX:+PrintGCDetails "-XX:OnOutOfMemoryError=C:\Users\haiyang.wen\Desktop\dump.bat %p"
 *  dump.bat内容："C:\Program Files\Java\jdk1.8.0_162\bin\jstack" -F >> C:\Users\haiyang.wen\Desktop\heap_oom.txt
 *  TODO 这里还没调通
 *
 * @author wenhy
 * @date 2018/8/17
 */
public class HeapAreaOomTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(5_000_000);
        while (true) {
            list.add(new String("测试测试"));
        }
    }

}
