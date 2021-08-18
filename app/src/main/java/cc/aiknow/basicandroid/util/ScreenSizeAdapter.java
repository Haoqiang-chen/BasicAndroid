package cc.aiknow.basicandroid.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * @Description: 参考字节跳动的屏幕适配工具类
 * 字节方案：https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 * 主要思想是：根据设计图的大小计算出符合当前设备的density值，然后赋值给Application和Activity的Resources#getDisplayMetrics中
 *
 * DisplayMetrics.density // 设备的density值  px = dp * (dpi/160)、dpi/160 = density
 * DisplayMetrics.densityDpi  // 设备的dpi（每英寸点数）  dpi = 开平方(x^2 + y^2) / size   size为设备对角线长度 单位为英寸
 * DisplayMetrics.scaledDensity // 字体的缩放值 一般情况下等于density的值，当时也可能不想等，并且当用户修改系统字体大小时会改变该值
 *
 * @Author chenhaoqiang
 * @Since 2021/8/17
 * @Version 1.0
 */
public class ScreenSizeAdapter {
    private static float mDensity;
    private static float mScaledDensity;

    /**
     * 根据设计图的宽或者高的dp值来计算目标设备的density值
     * @param activity  当前activity
     * @param application application
     * @param designDp 宽或者高的设计图上的dp
     */
    public static void changeTargetDeviceDensity(Activity activity, Application application, int designDp) {
        if (designDp <= 0) {
            return;
        }
        final DisplayMetrics applicationDisplayMetrics = application.getResources().getDisplayMetrics();
        if (mDensity == 0) {
            mDensity = applicationDisplayMetrics.density;
            mScaledDensity = applicationDisplayMetrics.scaledDensity;
            // 监听系统字体变化，重新获取设备的scaledDensity值，防止用户更改字体大小后，适配不生效
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        mScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        // 计算目标设备的Density值
        final float targetDeviceDensity = applicationDisplayMetrics.widthPixels / (designDp * 1.0f);
        // 字体的scaledDensity的值一般等于density，但也可能不想等，所以通过计算之前的比例，然后再计算目标设备上的字体scaledDensity
        final float targetDeviceScaledDensity = targetDeviceDensity * (mScaledDensity / mDensity);
        final int targetDeviceDensityDpi = (int) (targetDeviceDensity * 160);

        // 修改目标设备的值
        applicationDisplayMetrics.density = targetDeviceDensity;   // 设备的density值  px = dp * (dpi/160)、dpi/160 = density
        applicationDisplayMetrics.densityDpi = targetDeviceDensityDpi;  // 设备的dpi（每英寸点数）  dpi = 开平方(x^2 + y^2) / size   size为设备对角线长度 单位为英寸
        applicationDisplayMetrics.scaledDensity = targetDeviceScaledDensity;  // 字体的缩放值 一般情况下等于density的值，当时也可能不想等，并且当用户修改系统字体大小时会改变该值

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = mDensity;
        activityDisplayMetrics.densityDpi = targetDeviceDensityDpi;
        activityDisplayMetrics.scaledDensity = targetDeviceScaledDensity;
    }
}
