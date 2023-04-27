package cc.aiknow.basicandroid.androidvieweventandanima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import cc.aiknow.basicandroid.R;

public class ScaleTweenActivity extends AppCompatActivity {

    ImageView img1;
    ImageView img2;
    ScaleAnimation scaleAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_tween);

        img1 = findViewById(R.id.image1);
        img2 = findViewById(R.id.image2);
        scaleAnimation = new ScaleAnimation(1,1.2f,1,1.2f,150,150);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img2.startAnimation(scaleAnimation);
            }
        });
    }
}