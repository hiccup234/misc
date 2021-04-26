package top.hiccup.jdk.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 成员变量测试类
 *
 * 1.getFields()，获取所有的public成员变量，包括继承的
 * 2.getDeclaredFields()，获取本类(接口)中定义的成员变量，不包括继承的
 *
 * ==========================================================
 * Tips: 方法名中带Declared的方法不会返回父类的成员，但是会返回私有成员
 *       而不带Declared的则相反
 * ==========================================================
 *
 * @author wenhy
 * @date 2018/8/30
 */
public class FieldTest {

    static class Inner {
        private static String name = "name";
    }

    public static void fieldTest() throws NoSuchFieldException {
        Class clazz = Inner.class;
        Field field = clazz.getDeclaredField("name");
        System.out.println(field.isAccessible());
        System.out.println(field.getModifiers());
        // 普通的private可以通过setAccessible()，但是被final修饰的字段还需要通过特殊的操作
        boolean isAccessible = field.isAccessible();
        try {
            field.setAccessible(true);
            field.set(null, "hiccup");
            System.out.println(Inner.name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            // 这里一定要记得设置回原访问权限
            field.setAccessible(isAccessible);
        }
    }

    static class FinalInner {
        private static final String name = "final name";
    }

    public static void finalFieldTest() throws NoSuchFieldException, IllegalAccessException {
        Class clazz = FinalInner.class;
        Field field = clazz.getDeclaredField("name");
        System.out.println(field.isAccessible());
        System.out.println(field.getModifiers());
        boolean isAccessible = field.isAccessible();
        int modifiersBak = field.getModifiers();
        // 通过反射直接修改Field类的modifiers字段的访问权限
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        if (Modifier.isFinal(modifiersBak)) {
            // 如果判断出字段是被final修饰的，则直接修改修饰符modifiers的值
            // 这里不能直接设置为Modifier.PUBLIC，因为Modifier的isFinal方法： return (mod & FINAL) != 0;
//            modifiersField.setInt(field, Modifier.PUBLIC);
            modifiersField.setInt(field, modifiersBak & ~Modifier.FINAL);

        }
        try {
            field.setAccessible(true);
            field.set(null, "final hiccup");
            System.out.println(FinalInner.name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            // 这里一定要记得设置回原访问权限
            modifiersField.setInt(field, modifiersBak);
            modifiersField.setAccessible(false);
            field.setAccessible(isAccessible);
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        fieldTest();
        System.out.println("=================================");
        finalFieldTest();
    }
}
