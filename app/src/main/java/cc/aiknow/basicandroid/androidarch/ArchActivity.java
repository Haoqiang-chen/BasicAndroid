package cc.aiknow.basicandroid.androidarch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cc.aiknow.basicandroid.R;
import cc.aiknow.basicandroid.androidarch.lifecycle_awre_components.MyActivityObserver;
import cc.aiknow.basicandroid.androidarch.lifecycle_awre_components.MyLifecycleOwner;
import cc.aiknow.basicandroid.androidarch.livedata_viewmodel.MyViewModel;

/**
 * @Description: 用于学习Android架构组件的相关知识
 * @Author: chen
 * @Date: 2019/7/17
 */

public class ArchActivity extends AppCompatActivity {
    private Button buttonCustomLifecycleOwner, liveDataButton;
    private TextView liveDataText;
    private MyViewModel viewModel;
    private SimpleDateFormat simpleDateFormat;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch);
        // 获取当前组件的Lifecycle对象
        Lifecycle lifecycle = getLifecycle();
        // 为当前组件的生命周期对象添加观察者
        lifecycle.addObserver(new MyActivityObserver(this));
        Toast.makeText(this, "Lifecycle持有者（被观察者）---create", Toast.LENGTH_SHORT).show();
        initView();
        initListener();
        // 若发生配置变更等新,创建的Activity实例会接收到相同的ViewModel实例
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        // 获取LiveData实例并添加观察者
        // 当liveData中的数据发生改变会通知观察者，前提是观察者应处于活动状态（STARTED\RESUMED）
        viewModel.getLivedate().observe(this, new Observer<String>(){
            @Override
            public void onChanged(String s) {
                liveDataText.setText(s);
            }
        });
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
    }

    private void initView() {
        buttonCustomLifecycleOwner = findViewById(R.id.archCustomLifecycleOwner);
        liveDataButton = findViewById(R.id.liveDataButton);
        liveDataText = findViewById(R.id.liveDataText);
    }
    private void initListener() {
        buttonCustomLifecycleOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建自定义的Lifecycle持有者并为其添加观察者
                MyLifecycleOwner myLifecycleOwner = new MyLifecycleOwner(ArchActivity.this);
                myLifecycleOwner.getLifecycle().addObserver(new MyActivityObserver(ArchActivity.this));
                myLifecycleOwner.onStart();
            }
        });
        liveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 使用LiveData更新UI
                date = new Date(System.currentTimeMillis());
                String time = simpleDateFormat.format(date);
                viewModel.getLivedate().postValue("LiveData持有的数据发生改变\n" + time);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Lifecycle持有者（被观察者）---start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Lifecycle持有者（被观察者）---resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Lifecycle持有者（被观察者）---pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Lifecycle持有者（被观察者）---stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Lifecycle持有者（被观察者）---destroy", Toast.LENGTH_SHORT).show();
    }
}
