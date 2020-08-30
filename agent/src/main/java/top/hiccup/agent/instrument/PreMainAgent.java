package top.hiccup.agent.instrument;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * 一般包含如下[1][2]中一个方法即可。
 * <p>
 * public static void premain(String agentArgs, Instrumentation inst);  [1]
 * public static void premain(String agentArgs); [2]
 * 其中，[1] 的优先级比 [2] 高，将会被优先执行（[1] 和 [2] 同时存在时，[2] 被忽略）。
 * <p>
 * 在这个 premain 函数中，开发者可以进行对类的各种操作。
 * agentArgs 是 premain 函数得到的程序参数，随同 “– javaagent”一起传入。
 * 与 main 函数不同的是，这个参数是一个字符串而不是一个字符串数组，如果程序参数有多个，程序将自行解析这个字符串。
 * <p>
 * inst 是一个 java.lang.instrument.Instrumentation 的实例，由 JVM 自动传入。
 * java.lang.instrument.Instrumentation 是 instrument 包中定义的一个接口，也是这个包的核心部分，
 * 集中了其中几乎所有的功能方法，例如类定义的转换和操作等等。
 *
 * java -javaagent:agent-1.0-SNAPSHOT.jar -cp "D:\MyDev\Maven\m2\JDD\org\javassist\javassist\3.25.0-GA/javassist-3.25.0-GA.jar;D:\MyDev\IDEA Workspace\Hiccup\misc\agent\target\agent-1.0-SNAPSHOT.jar" top.hiccup.agent.MainApp
 *
 * @author wenhy
 * @date 2020/8/28
 */
public class PreMainAgent {

    static private Instrumentation inst = null;

    /**
     * This method is called before the application’s main-method is called,
     * when this agent is specified to the Java VM.
     **/
    public static void premain(String agentArgs, Instrumentation _inst) {
        System.out.println("PreMainAgent.premain() was called.");
        // Initialize the static variables we use to track information.
        inst = _inst;
        // Set up the class-file transformer.
        ClassFileTransformer trans = new MyClassFileTransformer();
        System.out.println("Adding a MyClassFileTransformer instance to the JVM.");
        inst.addTransformer(trans);
    }
}
