package cc.aiknow.basicandroid.androidarch.lifecycle_awre_components

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @Description: TODO
 * @Author chenhaoqiang
 * @Since 2021/3/8
 * @Version 1.0
 */
class ApplicationObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        Log.e("chenhaoqiang", "onBackground")
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        Log.e("chenhaoqiang", "onForeground")
    }
}