package cc.aiknow.basicandroid.androidview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

import cc.aiknow.basicandroid.R;

import static android.graphics.Bitmap.Config.ARGB_8888;

/**
 * @Description: 自定义view类
 * 主要用于演示绘制等方法的使用
 * @Author chenhaoqiang
 * @Since 2021/8/24
 * @Version 1.0
 */
public class MyDrawViewTwo extends View {

    private Paint mPaint;

    public MyDrawViewTwo(Context context) {
        this(context, null);
    }

    public MyDrawViewTwo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawLove(canvas);
//        drawPieChart(canvas, new int[]{10, 20, 30, 40}, 100);
//        drawStarByBitmap(canvas);
//        drawPathEffect(canvas);
//        drawTextInMiddleView(canvas);
        drawCanvasMethod(canvas);
    }

    /**
     * 绘制心形
     * @param canvas
     */
    private void drawLove(Canvas canvas) {
        /*
          不是子图形都需要使用 close() 来封闭。当需要填充图形时（即 Paint.Style 为 FILL 或 FILL_AND_STROKE），Path 会自动封闭子图形
          以下代码将画笔的绘制模式设置为STROKE就会发现，心形是不完整的
         */
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.addArc(200, 200, 400, 400, -225, 225);
        // forceMoveTo 标识画弧的时候是否需要抬下笔然后移动到绘制点(true)，还是拖着笔移动到绘制点(false)，当是拖着笔的时候可以理解为此图形和上一个添加的图形是联系在一起的子图形
        // 然后会默认进行图形封闭
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);
        canvas.drawPath(path, mPaint);
    }

    /**
     * 绘制一个饼状图，通过画扇形来实现
     * @param canvas
     * @param datas
     * @param sum
     */
    private void drawPieChart(Canvas canvas, int[] datas, int sum) {
        int w = getWidth();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int[] angles = new int[datas.length];
        int[] colors = new int[datas.length];
        float curAngle = 0;
        for (int i = 0; i < datas.length; i++) {
            angles[i] = (int) (((datas[i] * 1.0f) / (sum * 1.0f)) * 360);
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            colors[i] = color;
            mPaint.setColor(colors[i]);
            canvas.drawArc(0, 0, w, w, curAngle, angles[i], true, mPaint);
            curAngle += angles[i];
        }
    }

    /**
     * 绘制一个五角星并且用Bitmap进行着色
     * @param canvas
     */
    private void drawStarByBitmap(Canvas canvas) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        /**
         * 设置Bitmap着色
         * 其实就是相当于使用BItmap中像素颜色绘制图形，变相的相当于绘制特殊形状的图像
         * 注意当Paint使用Shader(着色器)时，其设置颜色的方法就不再起作用
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.top_imag);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(bitmapShader);
        Path path = new Path();
        path.moveTo(10, 100);
        path.lineTo(110, 100);
        path.lineTo(20, 200);
        path.lineTo(60, 20);
        path.lineTo(90, 200);
        path.lineTo(10, 100);
        canvas.drawPath(path, mPaint);
    }

    /**
     * 设置图形轮廓
     * @param canvas
     */
    private void drawPathEffect(Canvas canvas) {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        // PathEffect 会影响图形轮廓
        PathEffect pathEffect = new DashPathEffect(new float[]{20.0f, 5.0f, 30.0f, 10.0f}, 5);  // 第二个参数是是指虚线中第一个实线向左的偏移量
        mPaint.setPathEffect(pathEffect);
        mPaint.setColor(Color.BLUE);
        Path path = new Path();
        path.lineTo(10, 400);
        path.lineTo(100, 50);
        path.lineTo(300, 400);
        canvas.drawPath(path, mPaint);
    }


    /**
     * 居中绘制文字
     * @param canvas
     */
    private void drawTextInMiddleView(Canvas canvas) {
        mPaint = new Paint();
        mPaint.setTextSize(150);
        Paint.FontMetrics  fontMetrics = mPaint.getFontMetrics();
        // 控件高度的一半 + 文字高度的一半到基线的距离(文字高度的一半 - 基线到底部距离) = 在view中文字基线应该的位置
        float textBaseLine = getWidth() / 2.0f + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText("text", (getWidth() - mPaint.measureText("text")) / 2, textBaseLine, mPaint);
    }

    /**
     * Canvas中一些辅助绘制的方法，如平移、旋转、缩放、错切等。
     * 其中Camera可以辅助绘制三维变换，要注意Camera的坐标系
     * @param canvas
     */
    private void drawCanvasMethod(Canvas canvas) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.drawable_cartoon);
        Matrix resizeMatrix = new Matrix();
        resizeMatrix.postScale(200.0f/originBitmap.getWidth(), 200.0f/originBitmap.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(originBitmap, 0, 0 , originBitmap.getWidth(), originBitmap.getHeight(), resizeMatrix, true);

//        canvas.save();
//        canvas.translate(100, 0);
//        canvas.drawBitmap(bitmap, 0, 0, mPaint);
//        canvas.restore();

        canvas.save();
        Matrix matrix = new Matrix();
        matrix.postTranslate(300, 100);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();

        // Camera三维变换：这个看抛物线视频，讲的贼清楚！
        Camera camera = new Camera();

        canvas.save();
        camera.save();
        camera.rotateZ(30);
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(bitmap, 50, 50, mPaint);
        canvas.restore();
    }

    /**
     * View中的draw(Canvas canvas) 方法是绘制的总调度
     * 然后绘制顺序为：
     * 1. drawBackground 绘制背景 私有方法 不能被重写
     * 2. onDraw 绘制自身内容
     * 3. dispatchDraw 绘制子view
     * 4. onDrawForeground  绘制滚动条、前景
     *
     * 注意：
     * 1. ViewGroup的有些子类会不需要绘制自身，所以当重写这类组件的onDraw等绘制方法时，需要调用setWillNotDraw(false) 是的整个绘制流程可以正常执行
     * 2. ViewGroup 在初始化是默认设置不会绘制自身，具体实现在initViewGroup中，
     *    然后在draw(Canvas canvas, ViewGroup parent, long drawingTime) (View 中三个参数的draw方法) 中会判断是否需要进行绘制然后是调用draw()方法还是dispatchDraw()方法
     *    所以，当ViewGroup需要绘制自身时应该手动设置setWillNotDraw(false)，保证绘制流程可以完整执行
     * @param canvas
     */
    private void drawOrder(Canvas canvas) {

    }


}
