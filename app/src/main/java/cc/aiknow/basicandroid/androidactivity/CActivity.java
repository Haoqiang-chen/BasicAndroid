package cc.aiknow.basicandroid.androidactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cc.aiknow.basicandroid.R;

public class CActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        Toast.makeText(this, "C---create", Toast.LENGTH_SHORT).show();
        Log.i("C---", "create");
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

    // 该方法用于在Activity被销毁之前保存Activity的状态。默认会保存其中的Fragment和View
    // SDK28及之后该方法在onStop之后调用，28之前将在onStop之前调用，但是不保证一定onPause
    // 之前或者之后调用
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "saveInstanceState", Toast.LENGTH_SHORT).show();
    }

    // 当该Activity实例被重建的时候调用
    // 该方法在onStart回调函数之后，在onResume之前调用
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "reStoreInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "C---start", Toast.LENGTH_SHORT).show();
        Log.i("C---", "start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "C---resume", Toast.LENGTH_SHORT).show();
        Log.i("C---", "resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "C---pause", Toast.LENGTH_SHORT).show();
        Log.i("C---", "pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "C---stop", Toast.LENGTH_SHORT).show();
        Log.i("C---", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "C---destroy", Toast.LENGTH_SHORT).show();
        Log.i("C---", "destroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "C---Restart", Toast.LENGTH_SHORT).show();
        Log.i("C---", "Restart");
    }
}
