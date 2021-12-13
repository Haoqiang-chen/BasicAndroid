# 配置ndk-build中构建设置

# 指定构建so时不同CPU 对应的 ABI
APP_ABI := armeabi-v7a arm64-v8a

# 设置使用的C++标准库
APP_STL := c++_static

# 使用ndk-build命令在jni文件目录下执行，便可以构建出现动态链接库
# 会自动生成相应ABI的文件夹，也会生成构建过程中的临时产物，临时产物在onj文件夹下，可以删除
# 加载so时会根据手机CPU型号去相应目录下加载，并且只会在该目录下查找，如果查找不到，也不会去其他目录下查找，查找不到就会crash