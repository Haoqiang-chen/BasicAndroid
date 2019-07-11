package cc.aiknow.basicandroid.androidproceethread;


import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义线程类
 * 用于在线程中向主线程发送消息以此来更新主线程中的UI
 * @author chen
 * @version 1.0
 * @since 2019-06-17 12:29
 */
public class MyThreadToSendMessageToMainThread extends Thread {
    private Handler myHandler;

    MyThreadToSendMessageToMainThread(Handler handler) {
        this.myHandler = handler;
    }

    @Override
    public void run() {
        super.run();
        // 定时计时器每隔200毫秒使用handler向其绑定的消息队列发送消息
        for (;;) {
            myHandler.sendEmptyMessage(311);
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
