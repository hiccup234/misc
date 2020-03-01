package top.hiccup.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 对象工具类
 *
 * @author wenhy
 * @date 2018/3/11
 */
public final class ObjectUtils {

    /**
     * 设置为不可被实例化
     */
    private ObjectUtils() {
        throw new AssertionError();
    }

    /**
     * 深拷贝对象：通过Java对象序列化技术实现，要求被拷贝的对象的类实现Serializable接口
     *
     * @param object
     * @param <T>
     * @return
     */
    public static final <T extends Serializable> T deepClone(T object) {
        Object cloneObj = null;
        ByteArrayOutputStream bout = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bin = null;
        ObjectInputStream ois = null;
        try {
            // 对象输出到流
            bout = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bout);
            oos.writeObject(object);
            // 从流中读取对象
            bin = new ByteArrayInputStream(bout.toByteArray());
            ois = new ObjectInputStream(bin);
            cloneObj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bout) {
                    bout.close();
                }
                if (null != oos) {
                    oos.close();
                }
                if (null != bin) {
                    bin.close();
                }
                if (null != ois) {
                    ois.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) cloneObj;
    }

    /**
     * 对象类型转换
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T cast(Object obj, Class<T> clazz) {
        if (obj == null) {
            return null;
        } else if (!clazz.isAssignableFrom(obj.getClass())) {
            throw new RuntimeException(String.format("对象[%s]转换为[%s]失败", obj, clazz.getSimpleName()));
        } else {
            return (T) obj;
        }
    }

}
