package cc.aiknow.basicandroid.androidvieweventandanima;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import cc.aiknow.basicandroid.MyApplication;

public class EventViewOne extends View {
    private GestureDetector gestureDetector;
    private Paint mPaint;
    private RectF mRectF;

    public EventViewOne(Context context) {
        this(context, null);
    }

    public EventViewOne(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, onGestureListener);
        /**
         * 自定义view中应该在setOnTouchListener中接管MotionEvent
         * 非自定义view将onTouchEvent中的事件托管于手势检测器
         */
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        mPaint = new Paint();
        mRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(0xEA, 0xEA, 0xEA));
        mPaint.setColor(Color.BLUE);
        mRectF.set(0, 0, 200, 200);
        canvas.drawRoundRect(mRectF, 30, 30, mPaint); // 圆角通过设置椭圆半径来实现
    }

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("chenhaoqiang", "手势检测---down");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Toast.makeText(MyApplication.getContext(), "手势检测---up", Toast.LENGTH_SHORT).show();
            Log.e("chenhaoqiang", "手势检测---up");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };
}
