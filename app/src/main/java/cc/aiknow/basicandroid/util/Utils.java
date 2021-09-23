package cc.aiknow.basicandroid.util;

import android.app.ActivityManager;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.List;

import static android.text.TextUtils.isEmpty;

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

    public static boolean isJSONObject(String str) {
        if (isEmpty(str)) {
            return false;
        }
        boolean result = false;
        Object json = null;
        try {
            json = new JSONTokener(str).nextValue();
            if (json instanceof JSONObject) {
                result = true;
            } else if (json instanceof JSONArray) {
                result = false;
            }
        } catch (JSONException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
}
