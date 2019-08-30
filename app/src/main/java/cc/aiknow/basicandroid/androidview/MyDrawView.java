package cc.aiknow.basicandroid.androidview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

import cc.aiknow.basicandroid.R;

/**
 * 用于学习View的基本绘制流程以及绘制方法
 * @author chen
 * @version 1.0
 * @since 2019-08-23 19:29
 */
public class MyDrawView extends View {

    private Paint mPaint;
    private RectF mRectF;

    private int mWidth, mHeight;
    private Bitmap bitmapEvil;
    private int bitmapW, bitmapH;
    private Rect src,dest;
    private Context context;
    private int page = 1, loopNum = 1, flag = 0;

    private MyHandler myHandler = new MyHandler(this);

    // 解决handler内存泄露问题
    private static class MyHandler extends Handler{
        private final WeakReference<MyDrawView> mDraeView;

        MyHandler(MyDrawView myDrawView) {
            this.mDraeView = new WeakReference<>(myDrawView);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDraeView.get().invalidate();
            mDraeView.get().page++;
            if (mDraeView.get().page > 11) {
                mDraeView.get().page = 1;
                mDraeView.get().loopNum++;
            }
            mDraeView.get().flag = mDraeView.get().loopNum % 2 == 1? 0: 1;
            this.sendEmptyMessageDelayed(0, 100);
        }
    }

    // 单参数构造函数
    public MyDrawView(Context context) {
        this(context, null);
    }
    // 双参数构造函数，当在XML文件中进行定义view时调用
    public MyDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
        initRect();
        initBitmap();
    }

    // 用于获取自定义view的宽高精确值，以及测量模式
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight =  MeasureSpec.getSize(heightMeasureSpec);

    }

    // 当view大小发生改变时调用该方法，可获得改变后的宽高值
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    // 用于确定字view的位置
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    // 用于绘制自定义的view
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Canvas画布主要用于绘制基本形状，而形状的颜色、效果由Paint画笔实现
        // 可用画布直接使用颜色填充画布即绘制底色
        canvas.drawColor(Color.rgb(0xEA, 0xEA, 0xEA));
        // 示例：绘制叠加的矩形:首先绘制蓝色的圆角矩形，然后在其上方绘制半透明的黄色矩形
        mPaint.setColor(Color.BLUE);
        mRectF.set(0, 0, 200, 200);
        canvas.drawRoundRect(mRectF, 30, 30, mPaint); // 圆角通过设置椭圆半径来实现

        mPaint.setColor(Color.argb(0x40, 0xEE, 0xEE,0x00)); // 通过设置上方矩形颜色的透明度来实现半透明效果
        //mPaint.setColor(Color.rgb(0xEE, 0xEE,0x00));  // 通过设置画笔的透明度可实现通过设置颜色透明度一样的效果
        //mPaint.setAlpha(0x40);
        mRectF.set(50, 50, 250, 250);
        canvas.drawRoundRect(mRectF, 30, 30, mPaint);


        // Canvas图形变换以及画布状态保存、恢复以及图层示例
        canvas.save(); // 保存当前画布的坐标系状态
        // 保存当前画布状态后可依旧对画布进行操作
        canvas.translate((mWidth*1.0f)/2, (mHeight*1.0f)/2);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(0,0, 100, mPaint);
        canvas.restore(); // 恢复之前保存的坐标系状态
        // 恢复之后画布的坐标系状态为画布保存前的状态
        canvas.drawCircle(0,0, 50, mPaint); // 通过对比两个圆的位置可知画布坐标系已经恢复至之前的状态

        // 图层示例:默认画布只有默认图层，除此之外可使用saveLayer增加图层，此时图层进入到栈中，在恢复该图层之前的操作均在
        // 该图层上进行，恢复该图层后，该图层会覆盖到之前的图层之上
        canvas.saveLayerAlpha(0,0, mWidth*1.0f, mHeight*1.0f, 0x40,  Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.RED);
        canvas.translate((mWidth*1.0f)/2, (mHeight*1.0f)/2);
        canvas.drawCircle(0,0, 150, mPaint);
        canvas.restore();
        canvas.drawCircle(0,0, 40, mPaint);

        // canvas绘制图片及文字
        src.set((bitmapW / 11) * (page - 1), (bitmapH / 2) * flag, (bitmapW / 11) * page, (bitmapH / 2) * (flag + 1));
        dest.set(450,0, 450+ bitmapW/11, bitmapH/2);
        canvas.drawBitmap(bitmapEvil, src, dest, null);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
    }
    private void initRect() {
        mRectF = new RectF();
        src = new Rect();
        dest = new Rect();
    }

    private void initBitmap() {
        bitmapEvil = BitmapFactory.decodeResource(context.getResources(), R.drawable.evil);
        bitmapW = bitmapEvil.getWidth();
        bitmapH = bitmapEvil.getHeight();
    }

    public void loop() {
        myHandler.sendEmptyMessage(0);
    }
}
