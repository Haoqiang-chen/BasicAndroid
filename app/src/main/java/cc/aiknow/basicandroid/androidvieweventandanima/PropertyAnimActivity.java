package cc.aiknow.basicandroid.androidvieweventandanima;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import cc.aiknow.basicandroid.R;

/**
 * 属性动画property animation 学习
 */
public class PropertyAnimActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_anim);
        initView();
        initListener();
    }

    private void initView() {
        imageView = findViewById(R.id.property_image);
        btn1 = findViewById(R.id.btn1);
    }

    private void initListener() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f);
                objectAnimator.setDuration(3000);
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((ViewGroup)PropertyAnimActivity.this.getWindow().getDecorView()).removeView(imageView);
                    }
                });
                objectAnimator.start();
            }
        });
    }
}