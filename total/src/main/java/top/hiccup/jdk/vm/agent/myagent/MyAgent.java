package top.hiccup.jdk.vm.agent.myagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;


/**
 * JVM agent 两种模式：
 * <p>
 * 1、直接在命令行指定agent：java -javaagent:myagent.jar HelloWorld
 * a. 编写premain方法
 * b. 打成jar包，并且需要配置MANIFEST.MF配置文件：在~misc\src\main\java>目录下
 * jar cvmf ./top/hiccup/jdk/vm/agent/myagent/manifest.txt myagent.jar ./top/hiccup/jdk/vm/agent/myagent
 * 注意：manifest.txt结尾需要一个空行，不然生成的MANIFEST.MF不会把这些配置打进去
 * c. 命令行执行启动其他VM进程时加上参数：java -javaagent:myagent.jar HelloWorld
 * <p>
 * 2、Attach API
 * <p>
 * <p>
 * jar cvmf manifest.txt myagent.jar org/
 *
 * @author wenhy
 * @date 2019/5/1
 */
public class MyAgent {

    /**
     * 注意这里接收的参数不是类似main的String[]而是String类型
     *
     * @param args
     */
    public static void premain(String args) {
        System.out.println("agent premain");
    }

    public static void premain(String args, Instrumentation instrumentation) {
        // 采用字节码注入
        instrumentation.addTransformer(new MyTransformer2());
    }


    static class MyTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            System.out.printf("Loaded %s: 0x%X%X%X%X\n", className, classfileBuffer[0], classfileBuffer[1],
                    classfileBuffer[2], classfileBuffer[3]);
            return null;
        }
    }


    static class MyTransformer2 implements ClassFileTransformer, Opcodes {
        /**
         * 当遇到名字为main的方法的时候，在方法入口处注入“System.out.println(Hello, Instrumentation!)”
         */
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            ClassReader cr = new ClassReader(classfileBuffer);
            // TODO 高版本JDK已经出到ASM7
//            ClassNode classNode = new ClassNode(ASM7);
            ClassNode classNode = new ClassNode();
            cr.accept(classNode, ClassReader.SKIP_FRAMES);

            for (Object node : classNode.methods) {
                MethodNode methodNode = (MethodNode) node;
                if ("main".equals(methodNode.name)) {
                    InsnList instrumentation = new InsnList();
                    instrumentation.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                    instrumentation.add(new LdcInsnNode("Hello, Instrumentation!"));
//                    instrumentation
//                            .add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V"), false);
                    instrumentation
                            .add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V"));

                    methodNode.instructions.insert(instrumentation);
                }
            }

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classNode.accept(cw);
            return cw.toByteArray();
        }
    }


    static class MyTransformer3 implements ClassFileTransformer, Opcodes {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            if (className.startsWith("java") ||
                    className.startsWith("javax") ||
                    className.startsWith("jdk") ||
                    className.startsWith("sun") ||
                    className.startsWith("com/sun") ||
                    className.startsWith("org/example")) {
                // Skip JDK classes and profiler classes
                return null;
            }

            ClassReader cr = new ClassReader(classfileBuffer);
//            ClassNode classNode = new ClassNode(ASM7);
            ClassNode classNode = new ClassNode();
            cr.accept(classNode, ClassReader.SKIP_FRAMES);

            for (Object n : classNode.methods) {
                MethodNode methodNode = (MethodNode) n;
                for (AbstractInsnNode node : methodNode.instructions.toArray()) {
                    if (node.getOpcode() == NEW) {
                        TypeInsnNode typeInsnNode = (TypeInsnNode) node;

                        InsnList instrumentation = new InsnList();
                        instrumentation.add(new LdcInsnNode(Type.getObjectType(typeInsnNode.desc)));
//                        instrumentation.add(new MethodInsnNode(INVOKESTATIC, "org/example/MyProfiler", "fireAllocationEvent",
//                                "(Ljava/lang/Class;)V", false));
                        instrumentation.add(new MethodInsnNode(INVOKESTATIC, "org/example/MyProfiler", "fireAllocationEvent",
                                "(Ljava/lang/Class;)V"));

                        methodNode.instructions.insert(node, instrumentation);
                    }
                }
            }

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classNode.accept(cw);
            return cw.toByteArray();
        }
    }
}


class MyProfiler {
    public static ConcurrentHashMap<Class<?>, AtomicInteger> data = new ConcurrentHashMap<>();

    public static void fireAllocationEvent(Class<?> klass) {
        data.computeIfAbsent(klass, kls -> new AtomicInteger())
                .incrementAndGet();
    }

    public static void dump() {
        data.forEach((kls, counter) -> {
            System.err.printf("%s: %d\n", kls.getName(), counter.get());
        });
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(MyProfiler::dump));
    }
}