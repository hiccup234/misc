package top.hiccup.jdk.vm.jmm;

import java.util.ArrayList;
import java.util.List;

/**
 * OOM时dump内存快照
 *
 * @author wenhy
 * @date 2018/2/9
 */
public class DumpFileTest {

    public static void main(String[] args) {
        /**
         * @VM args:  -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=E:\Dump\dumpFileTest.dump
         */
        List list = new ArrayList();
        for(int i=0; i<5; i++) {
            list.add(new byte[5*1024*1024]);
        }
    }
}
