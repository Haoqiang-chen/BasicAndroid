package cc.aiknow.basicandroid.androidbroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * @Description: 自定的接受系统飞行模式的广播的广播接收器
 * @Author: chen
 * @Date: 2019/6/19
 */
class MySystemBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        // 当接收到用Intent封装的广播后被调用
        // 在清单文件中注册该广播，API文档中说当在清单文件中注册广播后
        // 应用安装后系统的包管理器会注册该广播接收器，所以当接收到相应
        // 的广播后会调用onReceive方法，若应用未启动则会启动应用(这个没有写成功的例子)
        val log = "接收系统广播：" + intent.action + if (intent.getBooleanExtra("state", false)) " 飞行模式已开启" else "飞行模式已关闭"
        Toast.makeText(context, log, Toast.LENGTH_SHORT).show()

    }
}
