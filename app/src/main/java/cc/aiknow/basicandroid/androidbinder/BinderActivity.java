package cc.aiknow.basicandroid.androidbinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import cc.aiknow.basicandroid.R;

public class BinderActivity extends AppCompatActivity {
    private IBinder serviceBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        // 启动绑定式服务
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BinderActivity.this, RemoteService.class);
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            }
        });
        // 客户端与Sever端进行信息交互
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceBinder != null) {
                    // 构造客户端数据
                    Parcel clientData = Parcel.obtain();
                    clientData.writeString("Hello");
                    // Sever端回复数据
                    Parcel replyData = Parcel.obtain();
                    try {
                        serviceBinder.transact(0, clientData, replyData, 0);
                        String replyMsg = replyData.readString();
                        Log.e("chenhaoqiang", "服务端返回数据：" + replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("chenhaoqiang", "服务端已经建连");
            serviceBinder = service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}