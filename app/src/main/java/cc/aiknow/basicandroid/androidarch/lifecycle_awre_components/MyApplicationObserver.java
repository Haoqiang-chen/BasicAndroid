package cc.aiknow.basicandroid.androidarch.lifecycle_awre_components;

import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import cc.aiknow.basicandroid.MyApplication;

/**
 * @Description: 应用程序的生命周期观察者
 * @Author chenhaoqiang
 * @Since 2021/1/19
 * @Version 1.0
 */
public class MyApplicationObserver implements LifecycleObserver {

//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    public void onCreate() {
//        Toast.makeText(MyApplication.getContext(), "Lifecycle观察者---create", Toast.LENGTH_SHORT).show();
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Toast.makeText(MyApplication.getContext(), "Lifecycle观察者---start", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Toast.makeText(MyApplication.getContext(), "Lifecycle观察者---resume", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Toast.makeText(MyApplication.getContext(), "Lifecycle观察者---pause", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Toast.makeText(MyApplication.getContext(), "Lifecycle观察者---stop", Toast.LENGTH_SHORT).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Toast.makeText(MyApplication.getContext(), "Lifecycle观察者---destroy", Toast.LENGTH_SHORT).show();
    }
}
