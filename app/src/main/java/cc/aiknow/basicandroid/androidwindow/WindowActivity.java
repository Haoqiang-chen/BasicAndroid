package cc.aiknow.basicandroid.androidwindow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import cc.aiknow.basicandroid.R;

/**
 * @Description: Window相关知识
 * @Author: chenhaoqiang
 * @Since: 2022/9/26
 * @Version: 1.0
 */
public class WindowActivity extends AppCompatActivity {

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        findViewById(R.id.showWindow).setOnClickListener(v -> showWindow());
        getBaseContext();
    }

    private void showWindow() {
        Button button = new Button(this);
        button.setText("按钮");
        WindowManager.LayoutParams wLp = new WindowManager.LayoutParams();
        getWindowManager().addView(button, wLp);
    }
}