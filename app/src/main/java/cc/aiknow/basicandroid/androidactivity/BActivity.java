package cc.aiknow.basicandroid.androidactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cc.aiknow.basicandroid.R;

public class BActivity extends AppCompatActivity {

    private Button activityCButton;
    private TextView activityBResultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Toast.makeText(this, "B ---create", Toast.LENGTH_SHORT).show();
        Log.i("B ---", "create");
        initView();
        initListener();
    }

    private void initView() {
        activityCButton = findViewById(R.id.activityCButton);
        activityBResultText = findViewById(R.id.activityBResultText);
    }

    private void initListener() {
        activityCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BActivity.this, CActivity.class);
                intent.putExtra("B", "使用startActivityForResult启动C");
                // 采用startActivityForResult
                startActivityForResult(intent, 311);
            }
        });
    }


    // 此方法将在Activity重新恢复到resume状态前被调用即onActivityResult方法会在onReStart之前调用
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 311 && resultCode == 113) {
            String result = data.getStringExtra("C");
            activityBResultText.setText(result);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "B ---start", Toast.LENGTH_SHORT).show();
        Log.i("B ---", "start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "B ---resume", Toast.LENGTH_SHORT).show();
        Log.i("B ---", "resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "B ---pause", Toast.LENGTH_SHORT).show();
        Log.i("B ---", "pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "B ---stop", Toast.LENGTH_SHORT).show();
        Log.i("B ---", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "B ---destroy", Toast.LENGTH_SHORT).show();
        Log.i("B ---", "destroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "B ---Restart", Toast.LENGTH_SHORT).show();
        Log.i("B ---", "Restart");
    }
}
