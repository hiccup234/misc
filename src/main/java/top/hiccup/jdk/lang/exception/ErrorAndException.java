package top.hiccup.jdk.lang.exception;

/**
 * Throwable作为Java异常体系的根类，直接继承自Object
 * 两个子类:Error, Exception
 *
 * 异常通常又分为：运行时异常和受查异常（chencked）
 *
 * 常见Error、运行时异常、受查异常：
 * 1.Error: OutOfMemoryError, StackOverFlowError
 *
 * 2.RunTimeException: NullPointerException, ClassCastException, ArrayIndexOutOfBoundsException
 *                     NoSuchMethodException, NumberFormatException,
 *
 * 3.CheckedException: IOException, 反射相关异常（NoSuchMethodException,NoSuchMethodException等）
 *
 * ===============================================================================
 * Q: NoClassDefFoundError与ClassNotFoundException的区别是什么?
 * A: 第一个是Error，是JVM或ClassLoader在加载类路径下的class文件时找不到依赖的类定义而抛出的，
 *    要查找的类在编译时是存在的，但是运行时却找不到了，一般是打包过程中漏掉了部分类或者漏掉Jar包或者Jar出现损坏等
 *
 *    第二个是Exception，一般是程序运行时动态加载类时抛出的异常，如：Class.forName("...");
 *    而且是一个受查异常
 * ===============================================================================
 *
 * @author wenhy
 * @date 2018/9/18
 */
public class ErrorAndException extends Throwable {
}
