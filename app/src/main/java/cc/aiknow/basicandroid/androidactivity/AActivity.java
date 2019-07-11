package cc.aiknow.basicandroid.androidactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cc.aiknow.basicandroid.R;
/**
 * @Description: Activity生命周期、启动模式的学习
 * @Author: chen
 * @Date: 2019/6/2
 */
public class AActivity extends AppCompatActivity {

    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Toast.makeText(this, "A---create", Toast.LENGTH_SHORT).show();
        Log.i("A---", "create");
        button1 = findViewById(R.id.acticityAButton1);
        button1.setOnClickListener(startActivityA);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "A---start", Toast.LENGTH_SHORT).show();
        Log.i("A---", "start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "A---resume", Toast.LENGTH_SHORT).show();
        Log.i("A---", "resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "A---pause", Toast.LENGTH_SHORT).show();
        Log.i("A---", "pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "A---stop", Toast.LENGTH_SHORT).show();
        Log.i("A---", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "A---destroy", Toast.LENGTH_SHORT).show();
        Log.i("A---", "destroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "A---Restart", Toast.LENGTH_SHORT).show();
        Log.i("A---", "Restart");
    }

    /**
     * 在Activity的启动模式为栈顶单例的情况下且位于栈顶或者启动模式为栈内单例或CLEAR_TOP的情况下调用此方法
     * 位于栈顶的Activity（启动模式为栈顶单例）被启动时
     * 将从onNewIntent方法接受到Intent
     * 即当启动模式为栈顶单例(singleTop)或者栈内单列(singleTask)或CLEAR_TOP的情况下启动响应的Activity时，生命周期变为先pause然后调用onNewIntent然后调用resume
     * 在调用该方法后getIntent方法依旧返回原始启动该Activity的Intent，若想更新Intent可以调用setIntent
     * setIntent可以更改由getIntent返回的Intent，经常与onNewIntent结合使用
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "栈顶单例模式接受Intent", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, getIntent().getStringExtra("source"), Toast.LENGTH_SHORT).show();
        // 更新Intent 否则getIntent依旧返回原始的Intent
        setIntent(intent);
        Toast.makeText(this, getIntent().getStringExtra("source"), Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener startActivityA = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.putExtra("source", "child Intent");
            i.setClass(AActivity.this, AActivity.class);
            startActivity(i);
        }
    };
}
