package com.hiccup.jdk.lang;

/**
 * 静态成员变量和成员方法的继承覆盖问题
 *
 * @author wenhy
 * @date 2018/1/24
 */
public class InheritTest {

    public static void main(String[] args) {
        System.out.println(Animal.age);
        // 如果子类没有覆盖父类的静态属性，则编译时链接到父类的静态属性
        System.out.println(Cat.color);
        // 表现出了子类覆盖父类静态属性，实际上访问静态域是前期绑定，如果访问的静态属性子类中没有再绑定到父类
        System.out.println(Cat.age);

        Animal animal = new Cat();
        System.out.println(animal.name+"\n\n");

        // 编译时绑定，通过类访问方法
        Animal.call();
        Cat.call();

        // 静态方法是前期绑定，
        // 多态访问也是访问到引用本身类型对应的静态方法
        Animal a = new Cat();
        // 这里会打印Animal中的"动物吼吼吼"
        a.call();

        // 通过对象引用调用静态方法（不会抛空指针，编译时已经绑定到类上）
        Animal animal2 = null;
        animal2.call();
    }

}

class Animal {
    public String name = "小动物";
    public static String color = "黑色";
    public static Integer age = 1;

    public static void call() {
        System.out.println("动物吼吼吼");
    }

    /**
     * private方法默认是final方法，编译时是前期绑定
     */
    private void f() {
        System.out.println("基类private f()");
    }
//    public static void main(String[] args) {
//        Animal animal = new Cat();
//        // 这里输出：基类private f()
//        animal.f();
//    }
}

class Cat extends Animal{
    public String name = "小猫";
    public static Integer age = 5;

    public void f() {
        System.out.println("导出类public f()");
    }

    public static void call() {
        System.out.println("小猫喵喵喵");
    }
}