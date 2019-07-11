package cc.aiknow.basicandroid.androidbroadcast

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cc.aiknow.basicandroid.R
import kotlinx.android.synthetic.main.activity_broadcast.*

/**
 * @Description: 用于学习广播接收器的页面
 * @Author: chen
 * @Date: 2019/6/19
 */
class BroadcastActivity : AppCompatActivity() {
//    private lateinit var myBroadcastReceiver:MyBroadcastReceiver
    private val myBroadcastReceiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast)
        initListener()
    }

    private fun initListener() {
        buttonBroadcastRegister.setOnClickListener {
            // 注册广播步骤
            // 首先定义Intent过滤器
            val intentFilter = IntentFilter().apply {
                addAction("broadcast")
            }
            // 使用registerReceiver注册广播实例
            // 动态注册的广播生命周期取决于注册该广播的上下文
            // 如在Activity上下文中注册，则在Activity销毁之前都可以接收广播
            // 若在Application上下文中注册广播则在应用运行期间都可以接收广播
            registerReceiver(myBroadcastReceiver, intentFilter, Manifest.permission.SEND_SMS, null)
            Toast.makeText(this, "广播接收器已注册", Toast.LENGTH_SHORT).show()
        }

        // 发送广播
        buttonSendBroadcast.setOnClickListener {
            Toast.makeText(this, "广播已发送", Toast.LENGTH_SHORT).show()
            // 发送广播步骤
            // 首先定义广播：用Intent进行封装
            val intent = Intent().apply {
                action = "broadcast"
                putExtra("broadcastData", "这是个广播！！")
            }
            // 使用sendBroadcast发送用intent封装好的广播
            sendBroadcast(intent)
        }

        buttonUnRegisterBroadcast.setOnClickListener {
            // 在需要的时候取消广播接收器的注册，以避免内存泄漏
            Toast.makeText(this, "广播接收器已取消注册", Toast.LENGTH_SHORT).show()
            unregisterReceiver(myBroadcastReceiver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
