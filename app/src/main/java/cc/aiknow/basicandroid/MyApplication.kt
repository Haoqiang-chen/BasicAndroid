package cc.aiknow.basicandroid

import android.app.Application
import android.content.Context
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
    }
}