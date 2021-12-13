package cc.aiknow.basicandroid.androidvieweventandanima;

import android.animation.IntEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import cc.aiknow.basicandroid.R;

/**
 * @Description: 带折叠、展开动画的TexView
 * @Author chenhaoqiang
 * @Since 2021/12/7
 * @Version 1.0
 */
public class AnimatorTextView extends androidx.appcompat.widget.AppCompatTextView {
    ValueAnimator objectAnimator;
    int start;
    int end;

    public AnimatorTextView(Context context) {
        this(context, null);
    }

    public AnimatorTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatorTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(Color.GRAY);
        gradientDrawable.setCornerRadius(20);
        setBackground(gradientDrawable);

        setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_mask_ad_fun_logo_ks), null, null, null);
    }

    public void initAnimator() {
        objectAnimator= ValueAnimator.ofInt(start, end);
        objectAnimator.setDuration(3000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setEvaluator(new IntEvaluator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();

                int curValue = (int) animation.getAnimatedValue();
                Log.e("chenhaoqiang", "curFraction:  " + animatedFraction + "   curValue:  " + curValue);
                ViewGroup.LayoutParams layoutParams = AnimatorTextView.this.getLayoutParams();
                layoutParams.width = curValue;
                AnimatorTextView.this.setLayoutParams(layoutParams);
            }
        });
    }

}
