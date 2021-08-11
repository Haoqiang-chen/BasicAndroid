package cc.aiknow.basicandroid.androidservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import cc.aiknow.basicandroid.androidaidl.IRemoteService;

public class MyRemoteService extends Service {


    public MyRemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IRemoteService.Stub() {
            @Override
            public String getTestString(String test) {
                return "这是服务端给你的数据~~" + test;
            }
        }.asBinder();
    }
}