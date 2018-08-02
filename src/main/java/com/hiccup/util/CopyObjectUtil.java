package com.hiccup.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 对象拷贝工具
 *
 * @author wenhy
 * @date 2018/3/11
 */
public class CopyObjectUtil {

    private CopyObjectUtil() {
        throw new AssertionError();
    }

    /**
     * 深拷贝对象
     * @param object
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends Serializable> T deepCloneObject(T object) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(object);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();

        // 说明：调用ByteArrayInputStream或ByteArrayOutputStream对象的close方法没有任何意义
        // 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，这一点不同于对外部资源（如文件流）的释放
    }
}
