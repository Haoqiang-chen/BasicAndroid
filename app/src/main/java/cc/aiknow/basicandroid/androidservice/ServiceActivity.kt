package cc.aiknow.basicandroid.androidservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cc.aiknow.basicandroid.databinding.ActivityServiceBinding
import java.lang.ref.WeakReference

/**
 * @Description: 学习Service的辅助Activity
 * @Author: chen
 * @Date: 2019/6/12
 */
class ServiceActivity : AppCompatActivity() {

    private var myBindService: MyBindService? = null
    private var isBind: Boolean = false
    private var isMessengerServiceBind: Boolean = false
    private var myServiceConnection: MyServiceConnection? = null
    private lateinit var binding: ActivityServiceBinding

    /**
     * 定义服务端的Messenger和客户端的Messenger
     */
    private var serviceMessenger: Messenger? = null
    private val clientMessenger: Messenger = Messenger(MyClientHandler(this))
    private var myMessengerServiceConnection: MyMessengerServiceConnection? =null

    companion object{
        @JvmField
        val FLAG_CLIENT = 0
    }

    private class MyClientHandler(val serviceActivity: ServiceActivity): Handler() {
        val serviceActivityWeakReference: WeakReference<ServiceActivity> = WeakReference(serviceActivity)
        override fun handleMessage(msg: Message) {
            msg?.let {
                if (msg.what == FLAG_CLIENT) {
                    Toast.makeText(serviceActivityWeakReference.get(), "服务端返回的数据", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        // 将Activity与绑定式服务进行绑定
        initBindService()
        // 采用Messenger进行通信的绑定式服务
        initBindServiceByMessenger()
    }

    private fun initBindService() {
        val intent = Intent().apply {
            setClass(this@ServiceActivity, MyBindService:: class.java)
        }
        myServiceConnection = MyServiceConnection()
        bindService(intent, myServiceConnection!!, Context.BIND_AUTO_CREATE)
    }

    private fun initBindServiceByMessenger() {
        val intent = Intent().apply {
            setClass(this@ServiceActivity, MyMessengerBindService::class.java)
        }
        myMessengerServiceConnection = MyMessengerServiceConnection()
        bindService(intent, myMessengerServiceConnection!! , Context.BIND_AUTO_CREATE)
    }
    /**
     * bindService方法中需要传入一个ServiceConnection对象作为参数，该参数用于监控客户端与服务器的连接
     */
    inner class MyServiceConnection: ServiceConnection{
        // 该方法在服务正常关闭的情况下不会被调用，只有在服务被意外关闭时才会被调用
        override fun onServiceDisconnected(name: ComponentName?) {
            isBind = false
        }

        // 绑定到服务时被调用
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBind = true
            myBindService = (service as MyBindService.MyBinder).myBindService
        }
    }

    /**
     * 采用Messenger进行交互式时的ServiceConnection
     */
    inner class MyMessengerServiceConnection: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isMessengerServiceBind = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceMessenger = Messenger(service)
            isMessengerServiceBind = true
        }

    }

    private fun initListener() {
        binding.intentServiceButton.setOnClickListener {
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
        binding.serviceButton.setOnClickListener {
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

        binding.foregroundServiceButton.setOnClickListener {
            val intent = Intent().apply {
                setClass(this@ServiceActivity, MyForegroundService::class.java)
                putExtra("foreground","这是传入的数据")
            }
            startService(intent)

        }
        /**
         * 测试绑定式服务
         */
        binding.bindServiceTestButton.setOnClickListener {
            if (isBind) {
                Toast.makeText(this, "服务已绑定，接收的服务数据为：  " + myBindService?.testData(), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "服务已解除绑定", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * 接触绑定式服务
         */
        binding.bindServiceUnbindButton.setOnClickListener {
            unbindService(myServiceConnection!!)
            isBind = false
        }

        /**
         * 采用Mesaenger与服务端进行通信
         */
        binding.bindServiceMessengerClientButton.setOnClickListener {
            if (isMessengerServiceBind) {
                val message = Message.obtain(null, MyMessengerBindService.FLAG_SERVICE, 0, 0)
                message.replyTo = clientMessenger
                serviceMessenger?.send(message)
            }
        }

        /**
         * 手动调起服务端的服务
         */
        binding.bindServiceRemoteButton.setOnClickListener {
            val intent = Intent().apply {
                setClass(this@ServiceActivity, MyRemoteService::class.java)
            }
            val connection = object: ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    Log.e("chenhaoqiang", "连接成功")
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                }

            }
            bindService(intent, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        layoutInflater
        myMessengerServiceConnection?.let {
            unbindService(it)
        }
    }
}
