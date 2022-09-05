package cc.aiknow.basicandroid.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * @Description: 圆形进度条
 * @Author: chen
 * @Date: 2021/12/19
 */
public class CustomCircleProgress extends View {

    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;
    private static final float DEFAULT_PROGRESS_MAX_VALUE = 100;

    private Paint circlePaint;

    private Paint arcPaint;

    private RectF arcRect;

    private float curProgress;


    public CustomCircleProgress(Context context) {
        this(context, null);
    }

    public CustomCircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setColor(Color.BLUE);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(20);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        arcRect = new RectF();

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.GREEN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(DEFAULT_WIDTH, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(DEFAULT_HEIGHT, MeasureSpec.EXACTLY));
            return;
        }
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(widthSpecSize, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(heightSpecSize, MeasureSpec.EXACTLY));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 当前进度所占白分比
        float radio = curProgress / DEFAULT_PROGRESS_MAX_VALUE;
        // 首先绘制外层进度条
        arcRect.set(0, 0, getWidth(), getHeight());
        canvas.drawArc(arcRect, 0, 360 * radio, false, arcPaint);
        // 绘制圆圈
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f - 20, circlePaint);
    }

    public void setCurProgress(float curProgress) {
        runAnimation(curProgress);
    }

    private void runAnimation(float curProgress) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(this.curProgress, curProgress);
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                CustomCircleProgress.this.curProgress = value;
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
