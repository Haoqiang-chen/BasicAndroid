package cc.aiknow.basicandroid.binder

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import android.util.Log
import java.io.FileDescriptor

/**
 * @Description: 创建一个运行在另一个进程的服务，用于模拟使用Binder进行通信
 * @Author: chenhaoqiang
 * @Since: 2023/7/13
 * @Version: 1.0
 */
class RemoteService : Service() {
    private val remoteServiceBinder = RemoteServiceBinder()

    override fun onBind(intent: Intent): IBinder {
        return remoteServiceBinder
    }

    private class RemoteServiceBinder : Binder() {
        // 实现响应通信方法
        //code : 要执行动作的标示
        //data : 从客户端往服务端传递的序列化后的数据，不能为空
        //reply : 从服务端返回的序列化后的数据，可能为空
        //附加操作标记：0-->表示阻塞等待该方法调用结束 1-->表示执行该方法后立即返回
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            val clientMsg = data.readString()
            Log.e("chenhaoqiang", "客户端消息为：$clientMsg")
            reply?.writeString("收到客户端消息，Bye")
            return true
        }
    }
}