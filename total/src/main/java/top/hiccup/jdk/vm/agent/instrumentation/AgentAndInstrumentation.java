package top.hiccup.jdk.vm.agent.instrumentation;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * 字节码注入
 *
 * @author wenhy
 * @date 2019/5/1
 */
public class AgentAndInstrumentation {


//    public static void premain(String args, Instrumentation instrumentation) {
//        instrumentation.addTransformer(new MyTransformer());
//    }
//
//    static class MyTransformer implements ClassFileTransformer {
//        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
//                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//            System.out.printf("Loaded %s: 0x%X%X%X%X\n", className, classfileBuffer[0], classfileBuffer[1],
//                    classfileBuffer[2], classfileBuffer[3]);
//            return null;
//        }
//    }

}
