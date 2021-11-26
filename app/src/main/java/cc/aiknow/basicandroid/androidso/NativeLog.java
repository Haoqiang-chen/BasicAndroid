package cc.aiknow.basicandroid.androidso;

/**
 * @Description: 采用动态链接库实现日志输出
 * @Author chenhaoqiang
 * @Since 2021/11/25
 * @Version 1.0
 */
public class NativeLog {

    static {
        System.loadLibrary("native-log");
    }

    /**
     * 定义的输出日志的JNI
     * @param tag 日志标签
     * @param message 日志消息
     */
    public native static void log(String tag, String message);
}
