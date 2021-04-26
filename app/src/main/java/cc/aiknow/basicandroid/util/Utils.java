package cc.aiknow.basicandroid.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * @Description: 工具类
 * @Author chenhaoqiang
 * @Since 2021/3/8
 * @Version 1.0
 */
public class Utils {

    // 获取当前上下文所在进程
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
