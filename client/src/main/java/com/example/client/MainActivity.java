package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import cc.aiknow.basicandroid.androidaidl.IRemoteService;

/**
 * 模拟客户端与服务端通过AIDL进行交互
 */
public class MainActivity extends AppCompatActivity {
    private IRemoteService iRemoteService;
    private RemoteServiceConnection remoteServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.hello_word).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("chenhaoqiang", "点击---开始启动远程服务");
                initBindService();
            }
        });

        findViewById(R.id.hello_word).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("chenhaoqiang", "长按---开始获取返回数据");
                try {
                    Log.e("chenhaoqiang", "远程服务中的数据：" + iRemoteService.getTestString("test"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    private void initBindService() {
        remoteServiceConnection = new RemoteServiceConnection();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("cc.aiknow.basicandroid", "cc.aiknow.basicandroid.androidservice.MyRemoteService"));
        boolean succeed = bindService(intent, remoteServiceConnection, Context.BIND_AUTO_CREATE);
        Log.e("chenhaoqiang", "" + succeed);
    }

    private class RemoteServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("chenhaoqiang", "启动远程服务");
            iRemoteService = IRemoteService.Stub.asInterface(service);
            try {
                Log.e("chenhaoqiang", "远程服务中的数据：" + iRemoteService.getTestString("test"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.e("chenhaoqiang", "onNullBinding");
        }
    }
}