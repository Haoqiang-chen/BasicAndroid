package cc.aiknow.basicandroid.androidservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * 基于Service的自定义服务类
 * <p>
 * 使用服务之前应该在清单文件中进行注册
 * 启动式服务可以使用广播来向启动该服务的客户端发送结果
 * 启动时服务必须管理自己的生命周期，即必须显示的关闭服务
 * 否则除非内存资源紧张，系统不会停止或者销毁服务
 * <p>
 * 示例：该服务可接受多个请求并在线程中依次执行，实现类
 * 似IntentService的功能
 *
 * @author chen
 * @version 1.0
 * @since 2019-06-18 19:14
 */
public class MyService extends Service {

    private MyHandler myHandler;
    private HandlerThread handlerThread;

    /**
     * 自定义用于和HandlerTread中Looper绑定的Handler
     */
    private final class MyHandler extends Handler {

        public MyHandler(Looper looper) {
            super(looper);
        }

        /**
         * 重写消息处理方法，该方法将在工作线程中执行
         *
         * @param msg
         */
        @Override
        public void handleMessage(final Message msg) {
            // 自定义需要在线程中执行的操作
            Log.i("MyService", "MyService依次执行Intent: " + msg.what);
            Toast.makeText(getApplication(), "MyService依次执行Intent: " + msg.what, Toast.LENGTH_SHORT).show();
            // 使用onStartCommand方法的startId以避免在
            // 执行其它操作时停止服务
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyService", "MyService create");
        Toast.makeText(this, "MyService create", Toast.LENGTH_SHORT).show();

        handlerThread = new HandlerThread("MyService's HandlerThread");
        handlerThread.start();
        myHandler = new MyHandler(handlerThread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 接受Intent请求并使用myHandler向线程发送消息
        int intentNum = intent.getIntExtra("IntentNum", -1);
        Message message = new Message();
        message.arg1 = startId;
        message.what = intentNum;
        myHandler.sendMessage(message);
        /**
         * onStartCommand()方法必须返回以下三种常量之一
         * 以描述系统在服务终止的情况下如何继续运行服务：
         * 1.START_NOT_STICKY：若有挂起的Intent的需要传递，则启动服务
         *
         * 2.START_STICKY：会重建服务并调用onStartCommand，挂起Intent
         * 依次传递，若没有挂起Intent则使用空Intent调用onStartCommand
         *
         * 3.START_REDELIVER_INTENT：重建服务，并通过传递给服务的最后
         * 一个Intent调用onStartCommand，任何挂起的Intent依次传递
         */
        return START_NOT_STICKY;
    }

    // onBind方法返回null即表示该服务不持支绑式服务
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MyService", "MyService destroy");
        Toast.makeText(this, "MyService destroy", Toast.LENGTH_SHORT).show();
    }
}
