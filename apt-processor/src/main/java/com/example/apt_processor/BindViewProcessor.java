package com.example.apt_processor;

import com.example.apt_annotation.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @Description: 注解处理类，用于根据注解生成相应的Java文件
 * 注：
 *
 * @Author chenhaoqiang
 * @Since 2021/5/17
 * @Version 1.0
 */
@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    /**
     * 可以用来打印错误信息
     */
    private Messager mMessager;

    /**
     * 是一个可以处理Element的工具类
     * Element(程序元素）：是指Java文件中的元素，比如包、类、方法、变量都可以称作是一个程序元素
     *        1. ExecutableElement：表示类或者接口中的方法、构造函数、初始化器({} 或者static{}) ，包括注解类型的元素
     *        2. PackageElement： 表示包元素，内部提供访问包和内部成员的入口
     *        3. TypeElement： 表示类或者接口元素，内部提供了类型信息和访问成员的入口，注意枚举是类，注解是接口
     *        4. VariableElement： 表示一个字段(Field)、枚举实例、方法或者构造函数中的参数、本地变量、资源变量、或者异常参数
     *        5. TypeParameterElement： 表示泛型类、接口、方法、构造函数中的类型参数
     */
    private Elements mElementUtils;

    /**
     * 采用Map存储每个类及其对应的Java代码生成代理类
     * key：类限定类名
     * value：ClassCreatorProxy
     */
    private Map<String, ClassCreatorProxy> mProxyMap = new HashMap<>();


    /**
     * 初始化：可以得到ProcessingEnvironment，ProcessingEnvironment提供很多有用的工具类Elements, Types 和 Filer
     * 例如：
     *     processingEnvironment.getElementUtils()  可以获得Elements 对象，封装了一系列实用的操作Element的方法
     *     processingEnvironment.getMessager()  获得Messager 来打印错误、警告、和其他信息  在编译的时候build窗口输出
     *
     * @param processingEnvironment
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
    }

    /**
     * 指定该注解处理器是注册给哪个注解使用的
     * 也可以使用@SupportedAnnotationTypes注解标记当前处理器可以处理哪些注解
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(BindView.class.getCanonicalName());
        return supportTypes;
    }

    /**
     * 指定使用的Java版本，通常这里返回SourceVersion.latestSupported()
     * 也可以使用@SupportedSourceVersion注解标记当前处理器支持的版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 可以在这里写扫描、评估和处理注解的代码，生成Java文件
     *
     * @param set
     * @param roundEnvironment  是一个接口，表示一个注解处理工具框架，可以为注解处理器提供一个实现了此接口的对象，以便APT可以查询有关注解的信息
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "APT---开始---APT");
        mProxyMap.clear();
        // 获取BindView注解绑定的所有程序元素
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element: elements) {
            VariableElement variableElement = (VariableElement) element; // 变量程序元素,获取注解修饰的变量元素
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement(); // 获取变量元素的父元素，即所在类
            String fullClassName = classElement.getQualifiedName().toString(); // 获取类型
            mMessager.printMessage(Diagnostic.Kind.NOTE, "getQualifiedName：  " + fullClassName);
            ClassCreatorProxy proxy = mProxyMap.get(fullClassName); // 获取这个类的代理类
            if (proxy == null) {
                proxy = new ClassCreatorProxy(mElementUtils, classElement);
                mProxyMap.put(fullClassName, proxy);
            }
            // 获取当前元素上的注解并存入代理类中
            BindView bindViewAnnotation = variableElement.getAnnotation(BindView.class);
            int id = bindViewAnnotation.value();
            proxy.putElement(id, variableElement);
        }

        // 循环创建Java文件
        for ( String key: mProxyMap.keySet()) {
            ClassCreatorProxy proxy = mProxyMap.get(key);
            mMessager.printMessage(Diagnostic.Kind.NOTE, "创建Java文件： " + proxy.getProxyClassFullName());
//            try {
//                JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(proxy.getProxyClassFullName(), proxy.getTypeElement());  // Filer用于创建新的资源、类或者其他文件
//                Writer writer = javaFileObject.openWriter();
//                writer.write(proxy.generateJavaCode());
//                writer.flush();
//                writer.close();
//            } catch (IOException e) {
//                mMessager.printMessage(Diagnostic.Kind.NOTE, "创建Java文件： " + proxy.getProxyClassFullName() + "  失败");
//                e.printStackTrace();
//            }

            try {
                JavaFile javaFile = JavaFile.builder(proxy.getPackageName(), proxy.generateJavaCLassByJavaPoet()).build();
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMessager.printMessage(Diagnostic.Kind.NOTE, "APT---结束---APT");
        return true;
    }
}
