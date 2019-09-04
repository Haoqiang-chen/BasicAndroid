package cc.aiknow.basicandroid.androidservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @Description: 创建绑定式服务
 * @Author: chen
 * @Date: 2019/8/30
 */

public class MyBindService extends Service {

    private MyBinder myBinder = new MyBinder();

    /**
     * 绑定式服务中需要返回一个IBinder的对象实例以此来与绑定是服务进行通信
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    /**
     * 需要创建一个Binder对象，该对象需要返回一个绑定式服务的对象以此来访问服务中提供的方法
     */
    class MyBinder extends Binder {
        public MyBindService getMyBindService() {
            return MyBindService.this;
        }
    }

    public String testData() {
        return "这是绑定式返回的数据";
    }
}
