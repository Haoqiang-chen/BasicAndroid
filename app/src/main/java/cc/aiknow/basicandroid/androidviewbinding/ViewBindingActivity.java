package cc.aiknow.basicandroid.androidviewbinding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cc.aiknow.basicandroid.R;
import cc.aiknow.basicandroid.databinding.ActivityViewBindingBinding;

public class ViewBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityViewBindingBinding bindingBinding = ActivityViewBindingBinding.inflate(getLayoutInflater());
        setContentView(bindingBinding.getRoot());
        bindingBinding.text.setText("这里是视图绑定页面");
    }
}