package com.hiccup.jdk.lang.reflect;

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
}
