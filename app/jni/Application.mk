# 配置ndk-build中构建设置

# 指定构建so时不同CPU 对应的 ABI
APP_ABI := armeabi-v7a arm64-v8a

# 设置使用的C++标准库
APP_STL := c++_static

# 使用ndk-build命令在jni文件目录下执行，便可以构建出现动态链接库
# 会自动生成相应ABI的文件夹，也会生成构建过程中的临时产物，临时产物在onj文件夹下，可以删除