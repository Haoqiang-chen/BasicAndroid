package cc.aiknow.basicandroid

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import cc.aiknow.basicandroid.androidarch.lifecycle_awre_components.ApplicationObserver
import cc.aiknow.basicandroid.androidso.NativeLog
import com.facebook.stetho.Stetho

/**
 * @author chen
 * @since 2019-06-03 19:54
 * @version 1.0
 */
class MyApplication: Application() {

    companion object {
        @JvmField
        var context: Context? = null

        @JvmStatic
        fun getContext(): Context? {
            return context;
        }
    }

    /**
     * 系统进程与Android应用进程之间的关系
     * 以冷启动为例：
     * 1. 系统进程开始启动应用，在创建应用进程之前，首先显示PreviewWindow(启动的Activity的主题中的windowBackground)
     * 2. 创建应用进程，创建应用进程后创建Application对象，并调用Application的 OnCreate() 方法
     * 3. 然后开始创建主线程，创建主Activity，执行主Activity的初始化，构造函数，然后调用onCreate方法
     * 4. 主Activity完成布局填充，首次绘制完成后（Displayed 时间戳出现位置），系统将隐藏PreviewWindow，然后显示主Activity页面
     */
    override fun onCreate() {
        NativeLog.log("start order", "Application onCreate start")
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        context = applicationContext;
        Log.e("chenhaoqiang", "Application Created")
        ProcessLifecycleOwner
                .get()
                .lifecycle
                .addObserver(ApplicationObserver())
        NativeLog.log("start order", "Application onCreate end")
    }
}