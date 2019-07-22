package cc.aiknow.basicandroid.androidarch.lifecycle_awre_components;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 自定义的生命周期的观察者即自定义的LifecycleObserver
 * 1.LifecycleObserver是一个接口无任何方法
 * 2.需要使用@OnLifecycleEvent(Lifecycle.Event.XXX)注解定义
 * 在被观察组件生命周期调用时需要响应的操作
 * @author chen
 * @version 1.0
 * @since 2019-07-17 19:09
 */
public class MyActivityObserver implements LifecycleObserver {
    private Context context;

    public MyActivityObserver(Context context) {
        this.context = context;
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Toast.makeText(context, "Lifecycle观察者---create", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Toast.makeText(context, "Lifecycle观察者---start", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Toast.makeText(context, "Lifecycle观察者---resume", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Toast.makeText(context, "Lifecycle观察者---pause", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Toast.makeText(context, "Lifecycle观察者---stop", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Toast.makeText(context, "Lifecycle观察者---destroy", Toast.LENGTH_SHORT).show();
    }

}
