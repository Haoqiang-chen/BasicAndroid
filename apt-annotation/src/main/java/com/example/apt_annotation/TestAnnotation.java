package com.example.apt_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 测试注解
 * @Author chenhaoqiang
 * @Since 2021/5/19
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
    int value() default -1;
}
