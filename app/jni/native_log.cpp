// JNI的C++实现
// Created by chenhaoqiang on 2021/11/25.
#include <android/log.h>
#include <native_log.h>
using namespace std;
// 如果Native代码是C或者C++语言，则需要使用extern "C"{} 将代码包裹起来
extern "C" {
// JNIEXPORT 与 JNICALL 不能省略，如果是C++语言，方法名称可以直接从生成的头文件中复制
// JNIEnv： 代码VM里的环境，表示 Java 调用 native 语言的环境，是一个封装了几乎全部 JNI 方法的指针,本地代码可以通过该参数与Java代码进行操作
// jclass： 暂时不了解
// 其余为JNI中对应的参数，头文件中只有参数类型，在实现时手动把参数补全即可
// 方法名称的格式： Java_JNI类的包名_类型_JNI方法名(参数)  注意：包名中的 . 需要替换成_  而下划线需要替换为 _1
JNIEXPORT void JNICALL Java_cc_aiknow_basicandroid_androidso_NativeLog_log(JNIEnv *env, jclass, jstring tag, jstring message) {
        // 将jstring转换成native字符
        const char *tagN = env->GetStringUTFChars(tag, 0);
        const char *messageN = env->GetStringUTFChars(message, 0);
        // 输出logcat日志
        __android_log_print(ANDROID_LOG_ERROR, tagN, "%s", messageN);
        // 释放字符串所占用内存
        env->ReleaseStringUTFChars(tag, tagN);
        env->ReleaseStringUTFChars(message, messageN);
    }
}

