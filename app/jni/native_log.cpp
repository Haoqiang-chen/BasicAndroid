// JNI的C++实现
// Created by chenhaoqiang on 2021/11/25.
#include <android/log.h>
#include <native_log.h>
using namespace std;
extern "C" {
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

