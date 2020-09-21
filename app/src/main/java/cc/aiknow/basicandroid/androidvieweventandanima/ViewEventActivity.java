package cc.aiknow.basicandroid.androidvieweventandanima;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cc.aiknow.basicandroid.R;

public class ViewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        initView();
    }

    private void initView() {
        findViewById(R.id.scrollViewBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ScrollActivity.class);
            }
        });
        findViewById(R.id.translateAnimBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TranslateAnimActivity.class);
            }
        });

        findViewById(R.id.frameAnimBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FrameAnimaActivity.class);
            }
        });

        findViewById(R.id.propertyAnimBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PropertyAnimActivity.class);
            }
        });
    }

    private void startActivity(Class<?> activityClass) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        startActivity(intent);
    }

}