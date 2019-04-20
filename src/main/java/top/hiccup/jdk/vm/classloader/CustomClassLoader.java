package top.hiccup.jdk.vm.classloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * 用户自定义类加载器，直接基础自抽象类ClassLoader
 *
 * =====================================================================================================================
 * findClass与loadClass的区别？
 *
 * findClass（）用于写类加载逻辑、loadClass（）方法的逻辑里如果父类加载器加载失败则会调用自己的findClass（）方法完成加载，保证了双亲委派规则。
 * 1、如果不想打破双亲委派模型，那么只需要重写findClass方法即可
 * 2、如果想打破双亲委派模型，那么就重写整个loadClass方法
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class CustomClassLoader extends ClassLoader {

    /**
     * 类加载器名称
     */
    private String name;
    /**
     * 要加载的类的路径
     */
    private String path;

    public CustomClassLoader(String name, String path) {
        super();
        this.name = name;
        this.path = path;
    }

    public CustomClassLoader(ClassLoader parent, String name, String path) {
        super(parent);
        this.name = name;
        this.path = path;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = findClassByte(name);
        return super.defineClass(name, data, 0, data.length);
    }

    private byte[] findClassByte(String name) {
        InputStream is = null;
        String className = name;
        className = className.replaceAll("\\.", "\\\\");
        String filePath = this.path + "\\" + className + ".class";
        File file = new File(filePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] tmp = new byte[1024];
            while (bis.read(tmp) != -1) {
                bos.write(tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bos.toByteArray();
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String path = "C:\\Ocean\\Work\\MyWork\\IntelliJ Workspace\\Hiccup2\\misc\\src\\main\\java";
        CustomClassLoader customClassLoader = new CustomClassLoader("customClassLoader", path);
        Class<?> clazz = customClassLoader.loadClass("top.hiccup.jdk.vm.classloader.Dog");
        Object obj = clazz.newInstance();
        System.out.println(clazz.getMethod("getName").invoke(obj));

        System.out.println(obj instanceof top.hiccup.jdk.vm.classloader.Dog);
    }
}


class Dog {

    private String name = "doge";
    private int age = 3;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}