package cc.aiknow.basicandroid.androidservice

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cc.aiknow.basicandroid.R
import kotlinx.android.synthetic.main.activity_service.*

/**
 * @Description: 学习Service的辅助Activity
 * @Author: chen
 * @Date: 2019/6/12
 */
class ServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        initListener()
    }

    fun initListener() {
        intentServiceButton.setOnClickListener {
            for (intentNum in 0 until 3) {
                /**
                 * 启动IntentService启动式服务：通过Intent指定需要启动的服务
                 */
                val i = Intent().apply {
                    setClass(this@ServiceActivity, MyIntentService::class.java)
                    putExtra("IntentNum", intentNum)
                }
                startService(i)
            }
        }
        serviceButton.setOnClickListener {
            for (intentNum in 0 until 3) {
                /**
                 * 启动Service启动式服务：通过Intent指定需要启动的服务
                 */
                val i = Intent().apply {
                    setClass(this@ServiceActivity, MyService::class.java)
                    putExtra("IntentNum", intentNum)
                }
                startService(i)
            }
        }

        foregroundServiceButton.setOnClickListener {
            val intent = Intent().apply {
                setClass(this@ServiceActivity, MyForegroundService::class.java)
                putExtra("foreground","这是传入的数据")
            }
            startService(intent)

        }
    }
}
