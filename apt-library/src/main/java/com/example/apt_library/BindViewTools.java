package com.example.apt_library;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description: TODO
 * @Author chenhaoqiang
 * @Since 2021/5/17
 * @Version 1.0
 */
public class BindViewTools {

    public static void Bind(Activity activity) {
        Class clazz = activity.getClass();
        Class bindViewClass = null;
        try {
            bindViewClass = Class.forName(clazz.getName() + "_ViewBinding");
            Method method = bindViewClass.getMethod("bind", activity.getClass());
            method.invoke(bindViewClass.newInstance(), activity);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
