package com.example.apt_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 自定义的BindView注解类
 * 注：
 * 元注解：
 *     1. 用于标记在注解上的注解，如使用在注解@Override上的@Target等注解
 *     2. 元注解的种类：
 *        @Retention: 注解的保留范围（RetentionPolicy中定义了常量）：SOURCE（源码可用）、CLASS（字节码/编译期可用）、RUNTIME（运行时可用）
 *                    SOURCE：源码可用的注解，作用就是标记
 *                    RUNTIME：运行时注解即可在运行时通过反射来实现注解处理器
 *                    CLASS：编译期注解是在编译期间实现注解处理器
 *        @Target：   注解的使用范围（ElementType定义了常量）： 默认可以修饰Java中的任何元素
 *        @Inherited  是否可以被继承：默认是false
 *        @Documented 是否会保存到JavaDoc文件中
 *
 * @Author chenhaoqiang
 * @Since 2021/5/17
 * @Version 1.0
 */
@Retention(RetentionPolicy.CLASS) // 编译时注解
@Target(ElementType.FIELD)  // 针对类的成员变量
public @interface BindView {
    int value(); // 注解值的的定义语法：类型 参数名() default 默认值  注： 默认值是可选的
}
