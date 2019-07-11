package cc.aiknow.basicandroid

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * @author chen
 * @since 2019-06-03 19:54
 * @version 1.0
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}