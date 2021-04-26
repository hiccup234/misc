package top.hiccup.jdk.vm.jmm;

/**
 * JSR133定义的happen-before规则
 *
 * 1、同一个线程中的每个操作都happens-before于出现在其后的任何一个操作。
 *   （不理解，所有的操作不都是在线程里吗？如果所有的线程都是顺序执行的，那哪里还有指令重拍呢？）
 *
 * 2、对一个监视器的解锁操作happens-before于每一个后续对同一个监视器的加锁操作。
 *   （如果对锁的释放操作被重排序到加锁操作之后的话就会引起死锁）
 *
 * 3、对volatile字段的写入操作happens-before于每一个后续的对同一个volatile字段的读操作。
 *   （如果对volatile的读被指令重排到写之前，则会导致没有读取到最新结果，线程可见性也无从谈起）
 *
 * 4、Thread.start()的调用操作会happens-before于启动线程里面的操作。
 *
 * 5、一个线程中的所有操作都happens-before于其他线程成功返回在该线程上的join()调用后的所有操作。
 *
 * 6、一个对象构造函数的结束操作happens-before与该对象的finalizer的开始操作。
 *
 * 7、传递性规则：如果A操作happens-before于B操作，而B操作happens-before与C操作，那么A动作happens-before于C操作。
 *
 * @author wenhy
 * @date 2019/4/6
 */
public class HappenBeforeTest {
}
