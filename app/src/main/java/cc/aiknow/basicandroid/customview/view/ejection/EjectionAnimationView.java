package cc.aiknow.basicandroid.customview.view.ejection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cc.aiknow.basicandroid.R;
import cc.aiknow.basicandroid.androidview.Switch;

/**
 * @Description: 弹射动画控件
 * @Author chenhaoqiang
 * @Since 2022/3/31
 * @Version 1.0
 */
public class EjectionAnimationView extends View {
    private MyHandler myHandler;
    private int viewW;
    private int viewH;
    private int interval = 1;
    private int anchorX;
    private int anchorY;
    private Switch addItemSwitch;
    private boolean canReleaseRes = false;


    private ArrayList<AbsEjectionItem> items;
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();

    public EjectionAnimationView(Context context) {
        this(context, null);
    }

    public EjectionAnimationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EjectionAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        myHandler = new MyHandler(this);
        preLoad();
    }

    private void preLoad() {
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_mask_zuma_emotion_1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_mask_zuma_emotion_2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_mask_zuma_emotion_3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_mask_zuma_emotion_4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_mask_zuma_emotion_5);
        bitmaps.add(bitmap1);
        bitmaps.add(bitmap2);
        bitmaps.add(bitmap3);
        bitmaps.add(bitmap4);
        bitmaps.add(bitmap5);
    }

    private void changeState() {
        canReleaseRes = true;
        for (AbsEjectionItem item: items) {
            if (item.lifeCycleState == LifeCycleState.ACTIVE) {
                canReleaseRes = false;
                item.changeState();
            }
        }
        if (canReleaseRes) {
            releaseRes();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewW = getMeasuredWidth();
        viewH = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (AbsEjectionItem item : items) {
            if (item.lifeCycleState == LifeCycleState.ACTIVE) {
                item.draw(canvas);
            }
        }
    }

    private void addItem() {
        if (addItemSwitch == Switch.OFF) {
            return;
        }
        Bitmap randomBitmap = bitmaps.get(new Random().nextInt(bitmaps.size()));
        items.add(new EjectionEmotion(randomBitmap, anchorX, anchorY, viewW, viewH));
        Log.e("chenhaoqiang", "添加Item");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        myHandler.removeMessages(0);
    }

    private void releaseRes() {
        interval = 1;
        items = new ArrayList<>();
        myHandler.removeMessages(0);
    }

    public void start() {
        myHandler.removeMessages(0);
        items = new ArrayList<>();
        addItemSwitch = Switch.ON;
        interval = 1;
        myHandler.sendEmptyMessage(0);
        Log.e("chenhaoqiang", "开始");
    }

    public void setAnchorPosition(int anchorX, int anchorY) {
        this.anchorX = anchorX;
        this.anchorY = anchorY;
    }

    public void stop() {
        addItemSwitch = Switch.OFF;
        Log.e("chenhaoqiang", "结束");
    }

    private static class MyHandler extends Handler {
        private final WeakReference<EjectionAnimationView> ejectionEmojiAnimation;

        MyHandler(EjectionAnimationView ejectionAnimationView) {
            this.ejectionEmojiAnimation = new WeakReference<>(ejectionAnimationView);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("chenhaoqiang", "处理消息");
            this.sendEmptyMessageDelayed(0, 10);
            if (ejectionEmojiAnimation.get().interval == 1) {
                ejectionEmojiAnimation.get().addItem();
            }
            ejectionEmojiAnimation.get().interval++;
            if (ejectionEmojiAnimation.get().interval == 17) {
                ejectionEmojiAnimation.get().interval -= 16;
            }
            ejectionEmojiAnimation.get().invalidate();
            ejectionEmojiAnimation.get().changeState();
        }
    }
}
