package cc.aiknow.basicandroid.androidvieweventandanima;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cc.aiknow.basicandroid.R;

public class ViewEventActivity extends AppCompatActivity {

    private AnimatorTextView animatorTextView;

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

        animatorTextView = findViewById(R.id.animatorTextView);
        animatorTextView.start = animatorTextView.getLayoutParams().width;
        animatorTextView.end = animatorTextView.start * 6;
        animatorTextView.initAnimator();
        animatorTextView.postDelayed(new Runnable() {
            @Override
            public void run() {
                animatorTextView.objectAnimator.start();
            }
        }, 5000);
    }

    private void startActivity(Class<?> activityClass) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        startActivity(intent);
    }

}