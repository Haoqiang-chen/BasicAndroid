package cc.aiknow.basicandroid.androidservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

/**
 * @Description: 采用Meaaenger与客户端进行通信的绑定式服务
 * @Author: chen
 * @Date: 2019/8/30
 */

public class MyMessengerBindService extends Service {
   public static final int FLAG_SERVICE = 0;
   private Messenger messenger;

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FLAG_SERVICE) {
                Toast.makeText(getApplicationContext(), "客户端发送的数据", Toast.LENGTH_SHORT).show();
                Message messageForClient = Message.obtain(null, ServiceActivity.FLAG_CLIENT, 0 , 0);
                try {
                   msg.replyTo.send(messageForClient);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        messenger = new Messenger(new MyHandler());
        return messenger.getBinder();
    }
}
