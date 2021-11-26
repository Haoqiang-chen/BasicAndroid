# 指定编译源码的配置信息

# 必须定义LOCAL_PATH, 用于表示源文件（Native文件）的文字，my-dir会返回Android.mk文件所在目录的路径
LOCAL_PATH := $(call my-dir)

# 用于清除LOCAL_XXX变量，例如；LOCAL_MODULE、LOCAL_SRC_FILES 和 LOCAL_STATIC_LIBRARIES
# 其中 CLEAR_VARS 指向的是一个特殊的Makefile，用来清除LOCAL_XXX变量
include $(CLEAR_VARS)

LOCAL_LDLIBS := -llog

# 定义构建的so的名称，生成的so会自动添加前缀和后缀，例如定义的LOCAL_MODULE为 native-log, 构建的动态链接库为 libnative-log.so
# 特殊的是如果定义的LOCAL_MODULE已经以lib为开头，那么构建出的动态链接库将不会添加lib前缀
LOCAL_MODULE := native-log

# 定义参与构建的源文件，即c或者c++的源文件，用空格分隔多个源文件
LOCAL_SRC_FILES := native_log.cpp

# BUILD_SHARED_LIBRARY会指向一个Makefile文件，会负责收集自从上次调用 include $(CLEAR_VARS)  后的所有LOCAL_XXX信息。并决定编译为什么
# BUILD_STATIC_LIBRARY：编译为静态库。
# BUILD_SHARED_LIBRARY ：编译为动态库
# BUILD_EXECUTABLE：编译为Native C可执行程序
include $(BUILD_SHARED_LIBRARY)

# 使用javah命令生成头文件
# 解释：javah -classpath 指定class路径(出现找不到class类路径时手动指定) -o 输出文件的名称  类全称
# javah -classpath /Users/chenhaoqiang/work/learn/BasicAndroid/app/build/intermediates/javac/minApi23DemoDebug/classes -o native_log.h cc.aiknow.basicandroid.androidso.NativeLog