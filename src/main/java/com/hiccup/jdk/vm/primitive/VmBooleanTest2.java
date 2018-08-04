package com.hiccup.jdk.vm.primitive;

/**
 * JVM中boolean类型测试2
 * 当成员变量为boolean,byte,char,short时，存储长度为其本身值域范围
 * 此时JVM会对iconst1,2,3做掩码运算（boolean是取低位最后一位）
 *
 * @author wenhy
 * @date 2018/8/1
 */
public class VmBooleanTest2 {

    private static boolean flag;

    public static void main(String[] args) {
        // 用asmtools修改为 2，3测试
        // 改为2时不会打印内容，改为3时又会打印，原因见类注释
        flag = true;
        if(flag) {
            System.out.println("Hello Java");
        }
        if(true == flag) {
            System.out.println("Hello JVM");
        }
    }

}
