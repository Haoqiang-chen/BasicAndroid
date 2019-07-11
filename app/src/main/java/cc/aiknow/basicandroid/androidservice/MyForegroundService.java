package cc.aiknow.basicandroid.androidservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * 自定义服务：用于显示在前台
 *
 * @author chen
 * @version 1.0
 * @since 2019-06-18 21:04
 */
public class MyForegroundService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "MyForegroundService create", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "MyForegroundService 执行操作 ：" + intent.getStringExtra("foreground"), Toast.LENGTH_SHORT).show();
        // 设置为前台服务
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(getApplicationContext())
                .setTicker("状态栏标题");
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        startForeground(1, notification);
        // 停止服务
        stopSelf();
        return START_NOT_STICKY;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "MyForegroundService destroy", Toast.LENGTH_SHORT).show();
    }
}
