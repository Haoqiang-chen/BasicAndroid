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

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        context = applicationContext;
        Log.e("chenhaoqiang", "Application Created")
        ProcessLifecycleOwner
                .get()
                .lifecycle
                .addObserver(ApplicationObserver())
    }
}