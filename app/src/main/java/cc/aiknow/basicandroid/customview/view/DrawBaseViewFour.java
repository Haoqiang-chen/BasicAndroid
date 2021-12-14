package cc.aiknow.basicandroid.customview.view;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import cc.aiknow.basicandroid.R;

/**
 * @Description: 绘制基础---Canvas中的辅助操作（范围裁切和几何变换）
 * 范围裁切：Canvas中的方法
 *         clipRect
 *         clipPath
 * 几何变换：
 *    二维变换：
 *        Canvas中的translate、rotate、scale、skew
 *        Matrix中的translate、rotate、scale、skew，以及自定义Matrix
 *    三维变换：
 *        Camera
 *
 * @Author chenhaoqiang
 * @Since 2021/12/13
 * @Version 1.0
 */
public class DrawBaseViewFour extends View {

    public DrawBaseViewFour(Context context) {
        this(context, null);
    }

    public DrawBaseViewFour(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawBaseViewFour(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        clip(canvas);
//        geometricTransformationByCanvas(canvas);
//        geometricTransformationByMatrix(canvas);
//        polyToPoly(canvas);
        threeDimensionByCamera(canvas);
    }

    /**
     * 使用Camera进行三维变换
     * Camera的坐标系：见图片camera_coordinate_sys
     * Camera的状态需要像Canvas一样保存
     * @param canvas
     */
    private void threeDimensionByCamera(Canvas canvas) {
        Camera camera = new Camera();
        // 绘制一个背景色 紫色背景
        canvas.drawColor(0xFFCABFFF);
        printMatrixValue(canvas);
        drawCoordinateSys(canvas, Color.BLACK);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.batman);
        canvas.drawBitmap(bitmap, 100, 100, new Paint(ANTI_ALIAS_FLAG));
        // 图片中心距离坐标系xy轴的距离
        float x = 100 + bitmap.getWidth() / 2;
        float y = 100 + bitmap.getHeight() / 2;

        /**
         * 旋转
         * rotate：沿三个轴宣州
         * rotateX:沿X轴旋转
         * rotateY:沿Y轴旋转
         * rotateZ:沿Z轴旋转
         */
//        canvas.save();
//        camera.save();
//        canvas.translate(x, y);
//        camera.rotate(30,0, 0);
//        camera.applyToCanvas(canvas);
//        canvas.translate(-x, -y);
//        canvas.drawBitmap(bitmap, 100, 100, new Paint(ANTI_ALIAS_FLAG));
//        camera.restore();
//        canvas.restore();

        /**
         * 移动: 对三维坐标系中的所有点都移动相应的距离
         * translate(float x, float y, float z);
         */
//        camera.save();
//        canvas.save();
//        camera.translate(100, 100, 0);  // 三维坐标系中坐标轴正负与View的坐标系不同
//        camera.applyToCanvas(canvas);
//        canvas.drawBitmap(bitmap, 100, 100, new Paint(ANTI_ALIAS_FLAG));
//        camera.restore();
//        canvas.restore();

        /**
         * 设置虚拟相机位置
         * setLocation 单位是英寸 且换算单位被Android 底层的图像引擎 Skia 写死为1英寸=72像素
         * 默认位置是 (0, 0, -8)（英寸）
         */
        canvas.save();
        camera.save();
        canvas.translate(x, y);
        camera.rotate(30,0, 0);
//        camera.setLocation(0,0, -300);
        camera.applyToCanvas(canvas);
        canvas.translate(-x, -y);
        canvas.drawBitmap(bitmap, 100, 100, new Paint(ANTI_ALIAS_FLAG));
        camera.restore();
        canvas.restore();







    }

    /**
     * Matrix中的自定义变换
     * setPolyToPoly: 是将一组坐标值改变成另一组坐标值，从而使图形形变
     * @param canvas
     */
    private void polyToPoly(Canvas canvas) {
        Matrix matrix = new Matrix();
        // 绘制一个背景色 紫色背景
        canvas.drawColor(0xFFCABFFF);
        printMatrixValue(canvas);
        drawCoordinateSys(canvas, Color.BLACK);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.batman);
        canvas.drawBitmap(bitmap, 100, 100, new Paint(ANTI_ALIAS_FLAG));

        canvas.save();
        float[] src = new float[]{100, 100, 0, 300, 300, 200, 400, 400};
        float[] dst = new float[]{0, 0, 100, 100, 0, 300, 300, 200, 400};
        matrix.setPolyToPoly(src, 0, dst, 0, 4);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, 100, 100, new Paint(ANTI_ALIAS_FLAG));
        canvas.restore();
    }

    /**
     * 通过Matrix进行几何变换
     * Matrix 是一个3 * 3的矩阵，设计成3 * 3是为了通过齐次坐标将矩阵加法转换成矩阵乘法
     * new Matrix() 会创建一个单位矩阵
     * @param canvas
     */
    public void geometricTransformationByMatrix(Canvas canvas) {
        // 绘制一个背景色 紫色背景
        canvas.drawColor(0xFFCABFFF);
        printMatrixValue(canvas);
        drawCoordinateSys(canvas, Color.BLACK);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.batman);
        canvas.drawBitmap(bitmap, 100, 100, new Paint(ANTI_ALIAS_FLAG));
        /**
         * Matrix平移
         */
//        canvas.save();
//        Matrix matrix = new Matrix();
//        matrix.postTranslate(150, 150);  // 设置矩阵中平移值  pre post方法单独用时效果一致
////        canvas.setMatrix(matrix); // 用参数中的Matrix替换原来的Matrix
//        canvas.concat(matrix);    // 原来的Matrix乘以参数的Matrix
//        drawCoordinateSys(canvas, Color.RED);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.batman), 100, 100, new Paint(ANTI_ALIAS_FLAG));
//        canvas.restore();

        /**
         * Matrix旋转
         */
//        canvas.save();
//        Matrix matrix = new Matrix();
//        matrix.preRotate(60);
//        canvas.concat(matrix);
//        drawCoordinateSys(canvas, Color.RED);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.batman), 100, 100, new Paint(ANTI_ALIAS_FLAG));
//        canvas.restore();

        /**
         * Matrix缩放
         */
//        canvas.save();
//        Matrix matrix = new Matrix();
//        matrix.preScale(0.5f, 0.5f);
//        canvas.concat(matrix);
//        drawCoordinateSys(canvas, Color.RED);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.batman), 100, 100, new Paint(ANTI_ALIAS_FLAG));
//        canvas.restore();

        /**
         * Matrix错切
         */
//        canvas.save();
//        Matrix matrix = new Matrix();
//        matrix.postSkew(0.5f, 0.5f);
//        canvas.concat(matrix);
//        drawCoordinateSys(canvas, Color.RED);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.batman), 100, 100, new Paint(ANTI_ALIAS_FLAG));
//        canvas.restore();

        /**
         * Matrix复合操作
         * 当前矩阵记为 I  变换矩阵记为 T  最终矩阵记为 M
         * pre:是指在计算最终的矩阵的时候，当前矩阵在左 ：  M = I * T
         * post:是指在计算最终的矩阵的时候，当前矩阵在右 ：  M = T * I
         * set： set方法相当于将当前矩阵直接变换为目标矩阵
         *
         *如何确定采用哪种方式的变换方法？
         * 思路：将变换操作分解出来 ，通过矩阵乘法算出最终变换矩阵的公式（变换矩阵在左侧时表示应用了该效果），然后在确定使用pre还是post
         * 参考：https://juejin.cn/post/7015593442248458270
         */
        canvas.save();
        Matrix matrix = new Matrix();
        matrix.preTranslate(100 + bitmap.getWidth() / 2, 100 + bitmap.getHeight() / 2);
        matrix.preRotate(30);
        matrix.preTranslate(-(100 + bitmap.getWidth() / 2), -(100 + bitmap.getHeight() / 2));
        canvas.concat(matrix);
        drawCoordinateSys(canvas, Color.RED);
        canvas.drawBitmap(bitmap, 100, 100, new Paint(ANTI_ALIAS_FLAG));
        canvas.restore();


    }

    private void printMatrixValue(Canvas canvas) {
        float[] values = new float[9];
        canvas.getMatrix().getValues(values);
        for (float value : values) {
            System.out.println(value);
        }
    }

    private void drawCoordinateSys(Canvas canvas, int color) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(5);
        canvas.drawLine(0,1, 800, 1, paint);
        canvas.drawLine(1,0, 1, 800, paint);
    }


    /**
     * Canvas中的几何变换
     * @param canvas
     */
    private void geometricTransformationByCanvas(Canvas canvas) {
        // 绘制一个背景色
        canvas.drawColor(0xFFCABFFF);  // 紫色背景

        /**
         * 平移变换：由于几何变换都是操作的Matrix，所以当平移变换后，整个坐标系都会移动
         */
        canvas.save(); // 保存当前坐标系的状态
        canvas.translate(50, 50);
        canvas.drawRect(0, 0, 50, 50, new Paint(ANTI_ALIAS_FLAG));  // 在新坐标系中绘制矩形
        canvas.restore(); // 恢复到之前保存的坐标系状态

        /**
         * 旋转变换:
         * 注意一下参数正负：顺时针为正向
         * px， py是旋转的轴心: 当需要原地旋转一个图形时，这个轴心应该设置为图形的中心，否则默认是原点
         */
        canvas.save();
        canvas.rotate(-60, 100, 100);  // 逆时针旋转
        canvas.drawRect(0, 0, 50, 50, new Paint(ANTI_ALIAS_FLAG));  // 在新坐标系中绘制矩形
        canvas.restore();

        /**
         * 缩放变换：
         */
        canvas.save();
        canvas.scale(1.5f, 1.5f);
        canvas.drawRect(0, 0, 50, 50, new Paint(ANTI_ALIAS_FLAG));  // 在新坐标系中绘制矩形
        canvas.restore();

        /**
         * 错切变换
         */
        canvas.save();
        canvas.skew(0.5f, 0.5f);
        canvas.drawRect(200, 200, 250, 250, new Paint(ANTI_ALIAS_FLAG));  // 在新坐标系中绘制矩形
        canvas.restore();


        /**
         * 多重变换的顺序：坐标系的变换顺序就是代码的顺序， 但是效果的实现上要注意变换的顺序
         * 比如：要将一个图形 平移到 一个位置 然后在这个位置进行旋转： 此时就不能先旋转 再平移， 如果先旋转的的话，会使坐标系方向变了，这之后在平移就会导致移动方向不正确
         */
        Paint rectPaint = new Paint(ANTI_ALIAS_FLAG);
        rectPaint.setStyle(Paint.Style.STROKE);

        canvas.save();

//        rectPaint.setColor(Color.RED);
//        canvas.drawRect(200, 200, 250, 250,rectPaint);
//        Paint linePaint = new Paint(ANTI_ALIAS_FLAG);
//        linePaint.setStyle(Paint.Style.STROKE);
//        linePaint.setStrokeWidth(10);
//        linePaint.setColor(Color.RED);
//        canvas.drawLine(0,0, 400, 0, linePaint);
//        canvas.drawLine(0,0, 0, 400, linePaint);
//
//        canvas.translate(100, 0);
//        linePaint.setColor(Color.GREEN);
//        rectPaint.setColor(Color.GREEN);
//        canvas.drawLine(0,0, 400, 0, linePaint);
//        canvas.drawLine(0,0, 0, 400, linePaint);
//        canvas.drawRect(200, 200, 250, 250, rectPaint);
//
//        canvas.rotate(45);
//        linePaint.setColor(Color.YELLOW);
//        rectPaint.setColor(Color.YELLOW);
//        canvas.drawLine(0,0, 400, 0, linePaint);
//        canvas.drawLine(0,0, 0, 400, linePaint);
//        canvas.drawRect(200, 200, 250, 250, rectPaint);

        rectPaint.setColor(Color.RED);
        canvas.drawRect(200, 200, 250, 250,rectPaint);
        Paint linePaint = new Paint(ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(10);
        linePaint.setColor(Color.RED);
        canvas.drawLine(0,0, 400, 0, linePaint);
        canvas.drawLine(0,0, 0, 400, linePaint);

        canvas.translate(100, 0);
        linePaint.setColor(Color.GREEN);
        rectPaint.setColor(Color.GREEN);
        canvas.drawLine(0,0, 400, 0, linePaint);
        canvas.drawLine(0,0, 0, 400, linePaint);
        canvas.drawRect(200, 200, 250, 250, rectPaint);

        canvas.rotate(45, 225, 225);
        linePaint.setColor(Color.YELLOW);
        rectPaint.setColor(Color.YELLOW);
        canvas.drawLine(0,0, 400, 0, linePaint);
        canvas.drawLine(0,0, 0, 400, linePaint);
        canvas.drawRect(200, 200, 250, 250, rectPaint);


        canvas.restore();


    }

    /**
     * 裁切
     * @param canvas
     */
    private void clip(Canvas canvas) {
        // 绘制一个背景色
        canvas.drawColor(0xFFCABFFF);  // 紫色背景
        canvas.save();
        /**
         * 矩形裁切：通过设置矩形范围将Canvas裁切为矩形范围的大小，再此之后绘制的内容都只会在该范围内绘制，超出矩形范围内的内容将不会被绘制
         * clipRect： 裁切得到指定矩形范围内的Canvas
         * clipOutRect： 裁切得到指定矩形范围之外的Canvas
         */
        canvas.clipRect(new Rect(0, 0, 50, 50));  // 裁切一个从原点开始计算的 50 * 50的矩形
        canvas.drawColor(Color.BLUE); // 给画布上整体绘制颜色，可以观察出最终只有50 * 50的矩形内被绘制了新的内容
        canvas.restore();

        canvas.save();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutRect(new Rect(0, 0, 50, 50));  // out方法为裁切得到指定矩形之外的Canvas
        }
        canvas.drawColor(Color.RED);
        canvas.restore();

        /**
         * 路径裁切：通过设置路径Path参数，裁切出路径之内或者之外的Canvas
         * clipPath：裁切得到指定Path范围内的Canvas
         * clipOutPath: 裁切得到指定范围之外的Canvas
         */
        canvas.save();
        Path path = new Path();
        path.addCircle(200, 200, 50, Path.Direction.CW);
        canvas.clipPath(path);
        canvas.drawColor(Color.GREEN);
        canvas.restore();

        canvas.save();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(path);
        }
        canvas.drawColor(Color.YELLOW);
        canvas.restore();
    }
}
