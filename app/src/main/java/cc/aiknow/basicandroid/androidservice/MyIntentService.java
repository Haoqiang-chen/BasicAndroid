package cc.aiknow.basicandroid.androidservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * IntentService服务是使用工作线程逐一处理各个启动请求的服务
 * <p>
 * 使用服务之前应该在清单文件中进行注册
 * 启动式服务可以使用广播来向启动该服务的客户端发送结果
 * <p>
 * IntentService服务具有以下特点：
 * 1.创建默认的工作线程，在主线程外执行传递给onStartCommand()方法的Intent
 * 2.创建工作队列，用于将Intent逐一传递给onHandleIntent方法执行
 * 3.在处理完所有的启动请求后，该服务被销毁，不需要手动销毁
 * 是因为在ServiceHandler中的handdMessage（）方法中先调用
 * onHandleIntent,接着调用stopSelf()
 * 4.提供onBind()方法的默认实现,返回null，即不可绑定
 * 5.提供onStartCommand()方法的默认实现可将Intent发送到Handler的消息队列
 * 然后handler的handleMessage方法被调用，该方法中会调用onHandleIntent方法
 *
 * @author chen
 * @version 1.0
 * @since 2019-06-18 15:18
 */
public class MyIntentService extends IntentService {
    static int count = 0;

    /**
     * 继承IntentService需要创建构造函数并向父类构造函数中
     * 传递工作线程的名称
     * Used to name the worker thread, important only for debugging.
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * 处理多个请求时该服务只被创建一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyIntentService", "IntentService create");
        Toast.makeText(this, "IntentService create", Toast.LENGTH_SHORT).show();
    }

    // 使用IntentService只需要重写onHandleIntent方法即可
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 定义具体逻辑代码
        // 匿名内部类访问外部类变量时 外部类变量应为final
        final int number = intent.getIntExtra("IntentNum", 0);
        if (number == count) {
            count++;
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplication(), "IntentService依次执行Intent: " + number, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MyIntentService", "IntentService destroy");
        Toast.makeText(this, "IntentService destroy", Toast.LENGTH_SHORT).show();
    }
}
