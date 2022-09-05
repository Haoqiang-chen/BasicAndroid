package cc.aiknow.basicandroid.androidvieweventandanima;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import cc.aiknow.basicandroid.R;

/**
 * 测试图片位置移动的属性动画
 */
public class ImgMoveActivity extends AppCompatActivity {
    private ViewGroup root;
    private ImageView imageView;
    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_move);
        root = findViewById(R.id.root);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAnimator.start();
            }
        });
        initView();
        initValueAnimator();
    }


    private void initView() {
        imageView = new ImageView(this);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(30, 30);
        imageView.setX(1000);
        imageView.setY(500);
        imageView.setBackgroundColor(Color.RED);
        root.addView(imageView, layoutParams);
    }

    private void initValueAnimator() {
        valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(new Point(600, 100)), new Point(1000, 500), new Point(200, 300));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point animatedValue = (Point) animation.getAnimatedValue();
                imageView.setX(animatedValue.x);
                imageView.setY(animatedValue.y);
            }
        });
        valueAnimator.setDuration(500);
    }


    private class BezierEvaluator implements TypeEvaluator<Point> {
        Point point;

        public BezierEvaluator(Point point) {
            this.point = point;
        }

        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {
            float x = ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * point.x + t * t * endValue.x);
            float y = ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * point.y + t * t * endValue.y);
            return new Point((int) x, (int)y);
        }
    }

    private class Point {
        float x;
        float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}