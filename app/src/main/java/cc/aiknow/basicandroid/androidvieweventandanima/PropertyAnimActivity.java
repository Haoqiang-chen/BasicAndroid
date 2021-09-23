package cc.aiknow.basicandroid.androidvieweventandanima;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import cc.aiknow.basicandroid.R;

/**
 * 属性动画property animation 学习
 * 属性动画分类：
 * 1. ViewPropertyAnimator，通过view.animate()方法获取，然后调用相应的动画方法，缺点是可选择的方法较少
 * 2. ObjectAnimator: 可自定义任何属性
 */
public class PropertyAnimActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btn1, btn2;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_anim);
        initView();
        initListener();
    }

    private void initView() {
        imageView = findViewById(R.id.property_image);
        view = findViewById(R.id.property_view);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
    }

    private void initListener() {
        // 基础属性动画
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 注意ObjectAnimator第二个参数属性名称是匹配的相应的setter方法，而不是类属性
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

        // 使用自定义求值器(TypeEvaluator)的属性动画
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    /**
     * 通过自定义求值器(TypeEvaluator)来实现根据不同类型计算时间完成度对应的属性完成度
     * 时间完成度 - 动画完成度(可通过插值器Interpolator计算) - 属性完成度(可通过求值器计算)  是具有对应关系的
     */
    private class PositionTypeEvaluator implements TypeEvaluator<PointF> {
        private PointF curPoint = new PointF();
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float x = startValue.x + ( endValue.x - startValue.x) * fraction;
            float y = startValue.y + (endValue.y - startValue.y) * fraction;
            curPoint.set(x, y);
            return curPoint;
        }
    }
}