package cc.aiknow.basicandroid.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import cc.aiknow.basicandroid.R;

/**
 * @Description: 弹射动画demo
 * @Author chenhaoqiang
 * @Since 2022/3/29
 * @Version 1.0
 */
public class EjectionEmojiAnimation extends View {
    private MyHandler myHandler;
    private int viewW;
    private int viewH;
    private int interval = 1;

    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> recycleItems = new ArrayList<>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();



    public EjectionEmojiAnimation(Context context) {
        this(context, null);
    }

    public EjectionEmojiAnimation(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EjectionEmojiAnimation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        myHandler = new MyHandler(this);
        preLoad();
    }

    public void preLoad() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zuma_emotion_1, options);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.zuma_emotion_2, options);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.zuma_emotion_3, options);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.zuma_emotion_4, options);
        bitmaps.add(bitmap);
        bitmaps.add(bitmap1);
        bitmaps.add(bitmap2);
        bitmaps.add(bitmap3);
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
        for (Item item : items) {
            if (item.state) {
                item.draw(canvas);
            }
        }
    }


    public void start() {
        myHandler.sendEmptyMessage(0);
    }

    // 解决handler内存泄露问题
    private static class MyHandler extends Handler{
        private final WeakReference<EjectionEmojiAnimation> ejectionEmojiAnimation;

        MyHandler(EjectionEmojiAnimation ejectionEmojiAnimation) {
            this.ejectionEmojiAnimation = new WeakReference<>(ejectionEmojiAnimation);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            this.sendEmptyMessageDelayed(0, 10);
            Log.e("chenhaoqiang", "当前时间" + System.currentTimeMillis());
            if (ejectionEmojiAnimation.get().interval == 1) {
                ejectionEmojiAnimation.get().addItem();
            }
            ejectionEmojiAnimation.get().interval++;
            if (ejectionEmojiAnimation.get().interval == 17) {
                ejectionEmojiAnimation.get().interval -= 16;
            }
            ejectionEmojiAnimation.get().changeState();
            ejectionEmojiAnimation.get().invalidate();
        }
    }

    private void changeState() {
        for (Item item: items) {
            if (item.state) {
                item.changeState();
            }
        }
    }

    private void addItem() {
        items.add(new Item());
        Log.e("chenhaoqiang", "数据个数：" + items.size());
    }


    private class Item {
        public int degree = 45;
        public int startX = 700;
        public int startY = 2000;
        public int x = startX;
        public int y = startY;
        public int width = 20;
        public int height = 20;
        public int radius = 90;
        public int distance = 0;
        public int breakDis = 0;  // 经过拐折点的距离
        public int v = 30;
        public int a = -1;
        public int directionH = 1;  // 1 是上方向
        public int directionW = 1; // 1 是向右
        private Paint paint;
        private int timer;
        private int alpha = 255;
        private float alphaStep = 255 / 100;
        private long startTime;
        private CountDownTimer countDownTimer;
        private Bitmap bitmap;
        private boolean state = true;
        private int rightBoundary;
        private int bottomBoundary;
        private int t = 0;
        private int rotateDegree = 0;
        private Random random;

        public Item() {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            random = new Random();
            // 随机发射角度
            degree = random.nextInt(180);

            if (degree < 90) {
                degree = 45;
                directionW = 1;
            } else {
                directionW = 2;
                degree = 120;
                degree = 180 - degree;
            }
            // 随机图像
            int randomBitmap = random.nextInt(bitmaps.size());
            bitmap = bitmaps.get(randomBitmap);

            startTime = System.currentTimeMillis();
            countDownTimer = new CountDownTimer(3000, 10) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished <= 2000) {
                        alpha -= alphaStep;
                        Log.e("chenhaoqiang", "alpha: " + alpha);
                    }
                    rotateDegree += 2;
                    if (rotateDegree > 360) {
                        rotateDegree = 0;
                    }
                }

                @Override
                public void onFinish() {
                    state = false;
                    Log.e("chenhaoqiang", "生命周期：" + (System.currentTimeMillis() - startTime));
                    Log.e("chenhaoqiang", "最终 alpha: " + alpha);
                }
            };
            countDownTimer.start();
            rightBoundary = viewW - radius;
            bottomBoundary = viewH - radius;
        }

        public void draw(Canvas canvas) {
            Log.e("chenhaoqiang", "绘制 alpha: " + alpha);
            if (alpha < 0) {
                alpha = 0;
            }
            paint.setAlpha(alpha);
            canvas.save();
//            canvas.drawCircle(x, y, radius, paint);
            canvas.rotate(rotateDegree, x + 82.5f, y + 82.5f);
            canvas.drawBitmap(bitmap, x, y, paint);
            canvas.restore();
        }

        public void changeState() {

            t += 10;
            // 计算当前时刻运动的总距离  s = Vot + 1/2 a * t * t
//            distance = (int)(v * t + (a * Math.pow(t, 2)) / 2);
            distance = (int)(v * t - (a * Math.pow(t, 2)) / 2);
            // 向上运动
            if (directionH == 1) {
                // 向右运动
                if (directionW == 1) {
                    // 计算当前x坐标位置
                    x = (int)((distance - breakDis) * Math.cos(Math.toRadians(degree))) + startX;
                    y = startY - (int)((distance - breakDis) * Math.sin(Math.toRadians(degree)));
                    Log.e("chenhaoqiang", "x: " + x + "    y: " + y);
                    // x 超过屏幕右侧位置
                    if (x >= rightBoundary) {
                        breakDis = distance;
                        directionW = 2;
                        startX = rightBoundary;
                        startY = y;
                        x = rightBoundary;
                    }
                    // y 超过屏幕上侧，方向改变
                    if (y <= 0) {
                        directionH = 2;
                        startX = x;
                        startY = 0;
                        y = 0;
                        breakDis = distance;
                    }
                } else {
                    // 向左运动
                    x = startX - (int)((distance - breakDis) * Math.cos(Math.toRadians(degree)));
                    y = startY - (int)((distance - breakDis) * Math.sin(Math.toRadians(degree)));
                    Log.e("chenhaoqiang", "x: " + x + "    y: " + y);
                    // x超过屏幕左侧
                    if (x <= 0) {
                        directionW = 1;
                        startX = 0;
                        startY = y;
                        x = 0;
                        breakDis = distance;
                    }
                    // y 超过屏幕上侧，方向改变
                    if (y <= 0) {
                        directionH = 2;
                        startY = 0;
                        startX = x;
                        y = 0;
                        breakDis = distance;
                    }
                }
            } else {
                // 向下运动
                // 向右运动
                if (directionW == 1) {
                    x = (int)((distance - breakDis) * Math.cos(Math.toRadians(degree))) + startX;
                    y = startY + (int)((distance - breakDis) * Math.sin(Math.toRadians(degree)));
                    Log.e("chenhaoqiang", "x: " + x + "    y: " + y);
                    // x 超过屏幕右侧位置
                    if (x >= rightBoundary) {
                        directionW = 2;
                        startX = rightBoundary;
                        startY = y;
                        x = rightBoundary;
                        breakDis = distance;
                    }

                    // y 超过屏幕底侧，方向改变
                } else {
                    // 向左
                    x = startX - (int)((distance - breakDis) * Math.cos(Math.toRadians(degree)));
                    y = startY + (int)((distance - breakDis) * Math.sin(Math.toRadians(degree)));
                    Log.e("chenhaoqiang", "x: " + x + "    y: " + y);
                    if (x <= 0) {
                        directionW = 1;
                        startX = 0;
                        startY = y;
                        x = 0;
                        breakDis = distance;
                    }
                    // y 超过屏幕底侧，方向改变
                }
                if (y >= bottomBoundary) {
                    directionH = 1;
                    startY = bottomBoundary;
                    startX = x;
                    y = bottomBoundary;
                    breakDis = distance;
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        myHandler.removeMessages(0);
    }
}
