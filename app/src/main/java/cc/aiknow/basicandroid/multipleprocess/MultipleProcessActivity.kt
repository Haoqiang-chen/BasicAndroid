package cc.aiknow.basicandroid.multipleprocess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import cc.aiknow.basicandroid.R
import cc.aiknow.basicandroid.androidarch.lifecycle_awre_components.ApplicationObserver
import cc.aiknow.basicandroid.util.Utils

class MultipleProcessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_process)
        Log.e("chenhaoqiang", Utils.getProcessName(this, android.os.Process.myPid()))
        ProcessLifecycleOwner
                .get()
                .lifecycle
                .addObserver(ApplicationObserver())
    }
}