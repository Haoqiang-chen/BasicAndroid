package cc.aiknow.basicandroid.androidlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cc.aiknow.basicandroid.R;

public class LayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        initView();
    }

    private void initView() {
        // CoordinatorLayout(协调者布局)
        findViewById(R.id.btn_coordinatorlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LayoutActivity.this, CoordinatorLayoutActivity.class);
                startActivity(intent);
            }
        });
    }
}