package top.hiccup.jdk.lang.exception;

/**
 * Throwable作为Java异常体系的根类，直接继承自Object
 * 两个子类:Error, Exception
 *
 * 异常通常又分为：运行时异常和受检查异常（checked）
 *
 * 常见Error、运行时异常、受检查异常：
 * 1.Error: OutOfMemoryError, StackOverFlowError, NoClassDefFoundError, InternalError, UnsupportedClassVersionError
 *
 * 2.RunTimeException: NullPointerException, ClassCastException, ArrayIndexOutOfBoundsException
 *                     NoSuchMethodException, NumberFormatException, ArithmeticException（除0）
 *
 * 3.CheckedException: IOException, InterruptedException, 反射相关异常（NoSuchMethodException,NoSuchMethodException等）
 *
 * try-catch 代码段会产生额外的性能开销，或者换个角度说，它往往会影响 JVM 对代码进行优化，
 * 所以建议仅捕获有必要的代码段，尽量不要一个大的 try 包住整段的代码；
 * 与此同时，利用异常控制代码流程，也不是一个好主意，远比我们通常意义上的条件语句（if/else、switch）要低效，
 * Java 每实例化一个 Exception，都会对当时的栈进行快照，这是一个相对比较重的操作。如果发生的非常频繁，这个开销可就不能被忽略了。
 *
 * @author wenhy
 * @date 2018/9/18
 */
public class ErrorAndExceptionTest extends Throwable {

}