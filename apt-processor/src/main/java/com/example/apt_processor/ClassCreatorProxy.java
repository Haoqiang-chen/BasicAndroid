package com.example.apt_processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * @Description: 创建Java代码的代理类，存放具体的创建Java代码的实现逻辑
 * @Author chenhaoqiang
 * @Since 2021/5/17
 * @Version 1.0
 */
public class ClassCreatorProxy {

    private String mBindingClassName; // 生成的Java文件类名
    private String mPackageName; // 原类的包名
    private TypeElement mTypeElement; // 类元素
    private Map<Integer, VariableElement> mVariableElementMap = new HashMap<>();


    public ClassCreatorProxy(Elements elementUtils, TypeElement classElement) {
        this.mTypeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(mTypeElement);
        String packageName = packageElement.getQualifiedName().toString();
        String className = mTypeElement.getSimpleName().toString();
        this.mPackageName = packageName;
        this.mBindingClassName = className + "_ViewBinding";
    }

    /**
     * id 和 变量元素的映射
     *
     * @param id
     * @param variableElement
     */
    public void putElement(int id, VariableElement variableElement) {
        mVariableElementMap.put(id, variableElement);
    }

    /**
     * 采用字符串创建Java类
     * @return
     */
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(mPackageName).append(";\n\n");
        builder.append("import com.example.apt_library.*;\n");
        builder.append('\n');
        builder.append("public class ").append(mBindingClassName);
        builder.append(" {\n");
        generateMethods(builder);
        builder.append('\n');
        builder.append("}\n");
        return builder.toString();
    }

    private void generateMethods(StringBuilder builder) {
        builder.append("public void bind(").append(mTypeElement.getQualifiedName()).append(" host ) {\n");
        for (int id : mVariableElementMap.keySet()) {
            VariableElement element = mVariableElementMap.get(id);
            String name = element.getSimpleName().toString();
            String type = element.asType().toString();
            builder.append("host.").append(name).append(" = ");
            builder.append("(").append(type).append(")(((android.app.Activity)host).findViewById( ").append(id).append("));\n");
        }
        builder.append("  }\n");
    }

    /**
     * 使用JavaPoet创建Java类
     *
     * @return 返回拼接好的Java类TypeSpec
     */
    public TypeSpec generateJavaCLassByJavaPoet() {
        TypeSpec generatedClass = TypeSpec.classBuilder(mBindingClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(generateJavaMethodCodeByJavaPoet())
                .build();

        return generatedClass;
    }


    private MethodSpec generateJavaMethodCodeByJavaPoet() {
        ClassName hostClassName = ClassName.bestGuess(mTypeElement.getQualifiedName().toString());
        MethodSpec methodSpec = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(hostClassName, "host")
                .addCode(generateCodeBlock())
                .build();
        return methodSpec;
    }

    private CodeBlock generateCodeBlock() {
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        for (int id : mVariableElementMap.keySet()) {
            VariableElement variableElement = mVariableElementMap.get(id);
            String varName = variableElement.getSimpleName().toString();
            String valType = variableElement.asType().toString();
            codeBlockBuilder.add("host." + varName + " = " + "(" + valType + ")" + "((android.app.Activity)host).findViewById($L);", id);
        }
        return codeBlockBuilder.build();
    }


    public String getProxyClassFullName() {
        return mPackageName + "." + mBindingClassName;
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }

    public String getPackageName() {
        return mPackageName;
    }
}
