package cc.aiknow.basicandroid.androidactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cc.aiknow.basicandroid.R;

public class CActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        setResult();
    }

    private void setResult() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("C", "这是从Activity C返回的数据");
        // 该方法是final的,返回结果会在Activity finish()方法中返回给调用方即会在Activity finish之前返回给调用方
        // 由于onActivityResult方法会在onResume方法之前调用（应该是在onReStart之前调用）
        // 所以setResult方法就不应该在opPause以及之后的生命周期回调中调用
        // 因为当按下返回键时会先执行finish()方法然后再执行onPause，而且返回结果会
        // 在Activity finish()中传递给前一个Activity，所以应注意setResult的调用时机
        setResult(113, resultIntent);
    }
}
