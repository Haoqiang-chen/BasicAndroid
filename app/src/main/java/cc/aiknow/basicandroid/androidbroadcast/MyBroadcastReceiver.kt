package cc.aiknow.basicandroid.androidbroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * @Description: 自定义的广播接收器用于接收自定义的广播
 * @Author: chen
 * @Date: 2019/6/19
 */
class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Toast.makeText(context, "接收到自定义广播：" + intent.getStringExtra("broadcastData"), Toast.LENGTH_SHORT).show()
        val pendingResult = goAsync()
        Thread(object: Runnable{
            override fun run() {
                Thread.sleep(100)
                Log.i("asd", "asd")
                pendingResult.finish()
            }
        }).start()
    }
}
