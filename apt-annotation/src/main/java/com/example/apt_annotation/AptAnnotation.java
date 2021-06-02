package com.example.apt_annotation;

import java.lang.reflect.Field;

public class AptAnnotation {

    @TestAnnotation(value = 100)
    private int field0;

    @TestAnnotation
    private int filed1;

    public static void main(String[] args) {
        try {
            Class clazz = Class.forName(AptAnnotation.class.getName());
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                TestAnnotation bindView = field.getAnnotation(TestAnnotation.class);
                System.out.println(bindView.value());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /**
         * getName: 是返回实体类型名称
         * getSimpleName: 返回实例在源码中声明的名称
         * getCanonicalName:是获取java语言规范定义的规范名称
         */
        System.out.println("测试一下Class名称方法");
        System.out.println(AptAnnotation.class.getName());  // om.example.apt_annotation.AptAnnotation
        System.out.println(AptAnnotation.class.getSimpleName());  // AptAnnotation
        System.out.println(AptAnnotation.class.getCanonicalName()); // com.example.apt_annotation.AptAnnotation

        System.out.println(Inner.class.getName());  // com.example.apt_annotation.AptAnnotation$Inner
        System.out.println(Inner.class.getSimpleName());  // Inner
        System.out.println(Inner.class.getCanonicalName()); // com.example.apt_annotation.AptAnnotation.Inner
    }

    class Inner {}
}