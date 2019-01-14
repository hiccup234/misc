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
 * 自定义类加载器
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class CustomClassLoader extends ClassLoader {

    /**
     * 加载器名称
     */
    private String name;
    /**
     * 加载类的路径
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
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = findClassByte(name);
        return super.defineClass(name, data, 0, data.length);
    }

    private byte[] findClassByte(String name) {
        InputStream is = null;
        String className = name;
        className.replaceAll("\\.", "\\");
        String filePath = this.path + className + ".class";
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
        String path = "C:\\Ocean\\Work\\MyWork\\IntelliJ Workspace\\Hiccup2\\misc\\src\\main\\java\\top\\hiccup\\jdk\\vm\\classloader>\n";
        CustomClassLoader customClassLoader = new CustomClassLoader("customClassLoader", path);
        Class<?> clazz = customClassLoader.loadClass("top.hiccup.jdk.vm.classloader.Dog");
        Object obj = clazz.newInstance();
        System.out.println(clazz.getMethod("getName").invoke(obj));
    }
}
