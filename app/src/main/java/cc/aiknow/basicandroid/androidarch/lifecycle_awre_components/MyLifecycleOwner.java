package cc.aiknow.basicandroid.androidarch.lifecycle_awre_components;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/**
 * 自定义的生命周期持有者即LifecycleOwner
 * 1.需要实现LifecycleOwner接口，仅用于表示该类为生命周期的持有者
 * 该接口中只包含getLifecycle()方法用于返回该组件的生命周期对象
 * 2.需要在自定义的生命周期持有中创建LifecycleRegister的实例并返回
 * 该类为Lifecycle抽象类的子类
 * 3.创建的Lifecycle对象应在相关生命周期函数中将Event转发给Lifecycle对象
 * @author chen
 * @version 1.0
 * @since 2019-07-17 19:29
 */
public class MyLifecycleOwner implements LifecycleOwner {
    private LifecycleRegistry lifecycleRegistry;
    private Context context;

    public MyLifecycleOwner(Context context) {
        lifecycleRegistry = new LifecycleRegistry(this);
        this.context = context;
    }

    public void onStart() {
        Toast.makeText(context, "自定义的Lifecycle持有者---start", Toast.LENGTH_SHORT).show();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
