package cc.aiknow.basicandroid.androidvieweventandanima;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cc.aiknow.basicandroid.R;

public class FrameAnimaActivity extends AppCompatActivity {

    private View frameView;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_anima);
        initView();
        initListener();
    }

    private void initView() {
        frameView = findViewById(R.id.frame_view);
        frameView.setBackgroundResource(R.drawable.frame_anima_test);
        btn1 = findViewById(R.id.btn1);
    }

    private void initListener() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 设置帧动画
                 */
                AnimationDrawable animationDrawable = (AnimationDrawable) frameView.getBackground();
                animationDrawable.start();
            }
        });
    }

}