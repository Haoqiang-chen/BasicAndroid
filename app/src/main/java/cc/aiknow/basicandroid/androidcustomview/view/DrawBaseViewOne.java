package cc.aiknow.basicandroid.androidcustomview.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import cc.aiknow.basicandroid.R;

/**
 * @Description: 绘制基础---Canvas中相关方法
 * @Author chenhaoqiang
 * @Since 2021/9/30
 * @Version 1.0
 */
public class DrawBaseViewOne extends View {
    /**
     * Paint意为颜料，主要设置颜色、风格(画笔粗细、实心粗心等)
     */
    private Paint mPaint;
    private int mWidth, mHeight;

    public DrawBaseViewOne(Context context) {
        this(context, null);
    }

    public DrawBaseViewOne(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawBaseViewOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight =  MeasureSpec.getSize(heightMeasureSpec);
    }

    /**
     * 自定义绘制时最常重写的方法就是onDraw
     * @param canvas 画布，用于进行绘制、辅助绘制（比如裁切、几何变换），绘制的方法一般都是drawXXX(), 裁切一般都是clipXXX()
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawColor(canvas);

//        float strokeWidth = somePaintApi();

//        drawCircle(canvas, strokeWidth);
//
//        drawRect(canvas);
//
//        drawPoint(canvas);
//
//        drawOval(canvas);
//
//        drawLine(canvas);
//
//        darwRoundRect(canvas);
//
//        drawArc(canvas);

        /**
         * Path中第一类直接描述图形路径例子
         */
//        drawPathOne(canvas);


//        drawPathTwo(canvas);

//        drawBitmap(canvas);

//        drawText(canvas);


    }

    private void drawText(Canvas canvas) {
        // 绘制文字时Paint Style样式如果是描边，则绘制出的文字也是描边
        mPaint.setTextSize(30);
        canvas.drawText("draw 画 文字 text", 0,50, mPaint);
    }

    private void drawBitmap(Canvas canvas) {
        /**
         * 绘制图片
         */
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.drawable_cartoon), 0, 0, mPaint);
    }

    private void drawPathTwo(Canvas canvas) {
        /**
         * Path中第二类方法是辅助绘制的方法或者设置
         * 以path.setFillType为例，用设置图形相交时如何填充图形，注意当Paint中setStyle为描边类型时该方法不起作用
         */
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.addCircle(200, 200, 50, Path.Direction.CW);
        path.addCircle(230, 200, 50, Path.Direction.CCW);
        // 奇偶原则：从图形中任意一点 p 向图形外发出一条射线，射线与图形边相交的个数若是奇数，那么认为该点 p 在最终图形的内部，在绘制时会被填充
//        path.setFillType(Path.FillType.EVEN_ODD);
        // 非零环绕数原则：此时图形是有绘制方向的，从从图形中任意一点 p 向图形外发出一条射线，遇到顺时针的边奇数加1，遇到逆时针的边奇数减一，若最后计数不为0则认为该点 p 位于最终图形的内部，在绘制时会被填充
        path.setFillType(Path.FillType.WINDING);
        canvas.drawPath(path, mPaint);
    }

    private void drawPathOne(Canvas canvas) {
        /**
         * 绘制自定义图形
         * 绘制自定义图形时可以使用Path对象，用于描述一组自定义的图形路径
         * Path对象中方法大概可以分为两类：
         *  第一类：直接描述路径的方法，此时又可以分为两组
         *   1. addXXX() 用于添加封闭的独立的子图形
         *   2. xxxTo() 用于绘制线或者曲线
         */
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        Path path = new Path();
        RectF arcRect = new RectF(0, 0 , 400, 400);
        // 例如添加三个独立的子图形后进行绘制
        path.addArc(arcRect, -90, 90);
        path.addCircle(200, 200, 50, Path.Direction.CW);
        path.addArc(arcRect, 90, 90);

        // 由当前位置向坐标(x,y)进行画一条直线，这个当前位置是Path中上一个子图形的最后绘制位置，若想移动当前位置，可以使用Path.moveTo(方法)
//        path.lineTo(200, 200); // 从当前位置绘制,该方法还有对应的rLineTo其中参数为根据当前位置的相对坐标，所谓当前位置，即最后一次调用画 Path 的方法的终点位置。初始值为原点
        path.moveTo(0,0);
        path.lineTo(200, 200); // 移动当前位置后再进行绘制
//        path.arcTo(arcRect, 0, 60, false); // 添加一个弧，其中参数forceMoveTo表示是需要将当前位置挪到弧的绘制起点（也可以理解为从当前起点绘制弧的时候是否需要先抬起笔再绘制弧，具体区别提现在当前位置与弧的起点之间是否会绘制连接线），注意Path中绘制弧并不会以当前位置为起点
        path.arcTo(arcRect, 0, 60, true); // 当强制将当前起点挪到弧的绘制起点后，就不会绘制之前的当前位置与弧之间的连接线
        path.close(); // 用于封闭当前子图形，即从当前位置向子图形的起点绘制一条直线，当画笔的STYLE是填充 或者 填充并且描边的时候 会自动封闭子图形
        canvas.drawPath(path, mPaint);
        // 绘制一个心形
        Path path1 = new Path();
        path1.addArc(0, 0, 200, 200, -225, 225);  // 首先添加一个圆弧子图形
        path1.arcTo(200, 0, 400, 200, -180, 225, false);  // 然后对称的画一个弧线
        path1.lineTo(200, 300); // 然后以第二个弧的终点为当前起点绘制到定点，实现半个心形
        path1.close(); // 最后将整个子图形进行封闭
        canvas.drawPath(path1, mPaint);
    }

    private void drawArc(Canvas canvas) {
        /**
         * 绘制扇形或者弧
         * 参数：分别是
         * 1. 扇形所在椭圆的四个坐标
         * 2. 初始角度(坐标轴x轴正向为0度，顺时针为正)
         * 3. 扇形划过的角度
         * 4. 是否将连接到椭圆圆心(圆椭圆圆心就是长轴短轴的交点)， true表示连接到圆心，此时绘制的是个扇形，false 则绘制的是个弧
         * 5. 自定义的Paint
         */
        RectF arcRecF = new RectF(200, 400, 600, 600);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(arcRecF, mPaint);  // 矩形
        canvas.drawOval(arcRecF, mPaint);  // 椭圆
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(arcRecF, 0, 60, true, mPaint); // 绘制扇形
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(arcRecF, -180, 120, false, mPaint); // 绘制弧
    }

    private void darwRoundRect(Canvas canvas) {
        /**
         * 绘制圆角矩形
         * 参数分别为矩形的四个坐标， rx 表示 圆角的横向半径  ry 表示 圆角的纵向半径
         * 这个圆角相当于四分之一个椭圆
         */
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(30, 400, 400, 600, 20, 40, mPaint);
        canvas.drawLine(50, 400, 50, 600, mPaint);
        canvas.drawLine(30, 440, 400, 440, mPaint);
    }

    private void drawLine(Canvas canvas) {
        /**
         * 绘制直线
         * 直线的线头形状可以通过setStrokeCap来设置
         * 由于直线不是封闭图形setStyle(style)对直线没有影响
         */
        mPaint.setStrokeWidth(2);
        canvas.drawLine(0, 30, 300, 30, mPaint);
        mPaint.setStrokeWidth(30);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(30, 30, 30, 300, mPaint); // 绘制一条直线
        float[] lines = new float[]{90, 30, 90, 300, 150, 30, 150, 300};
        canvas.drawLines(lines, 4, 4, mPaint); // 绘制一组直线  参数设置与批量绘制点一样
//        canvas.drawLines(lines, mPaint); // 绘制一组直线
        mPaint.setStrokeWidth(2);
        canvas.drawLine(0, 300, 300, 300, mPaint);
    }

    private void drawOval(Canvas canvas) {
        /**
         * 绘制椭圆，只能绘制横着或者竖着的椭圆，斜着的椭圆可以通过变换来实现
         * 椭圆的绘制就是先限制一个矩形，然后再进行绘制椭圆
         */
        RectF rectF = new RectF(50, 50, 350, 200);
        mPaint.setStrokeWidth(2);
        canvas.drawRect(rectF, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawOval(rectF, mPaint);  // 直接传入一个矩形进行绘制椭圆
//        canvas.drawOval(50, 50, 350, 200, mPaint);  // 传入矩形的坐标来绘制矩形 以上两种绘制方式效果相同
    }

    private void drawPoint(Canvas canvas) {
        /**
         * 绘制点或者一组点
         * 可以通过设置strokeCap来设置点的形状
         */
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);  // 设置点的形状：圆头、方头、平头,其中 SQUARE 和 BUTT 画出来的都是方形，该方法其实是用来设置线条终点形状的方法
//        mPaint.setStrokeCap(Paint.Cap.SQUARE);  // 设置点的形状：方头
//        mPaint.setStrokeCap(Paint.Cap.BUTT);  // 设置点的形状：平头  平头是线头会到终点就截止 而圆头和方头会超出终点进行绘制
        mPaint.setStrokeWidth(30);
        canvas.drawPoint((mWidth * 1.0f) / 2, (mHeight * 1.0f)/ 2, mPaint); // 绘制一个点
        float[] points = new float[]{0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 200, 200};
        canvas.drawPoints(points, 2, 8, mPaint); // 绘制一组点 参数分为别 点左边数组， offset表示取坐标时需要偏移几个数据， count表示一共需要的数据个数（count = 点个数 * 2）
//        canvas.drawPoints(points, mPaint); // 从数组中依次绘制出所有点，当数组长度为奇数时，最后一个点不会绘制
    }

    private void drawRect(Canvas canvas) {
        /**
         * 绘制矩形
         * 其他方法类似
         */
        canvas.drawRect(10, 10, 200, 200, mPaint);
    }

    private void drawCircle(Canvas canvas, float strokeWidth) {
        /**
         * 绘制圆形
         * 其他方法类似
         */
        canvas.drawCircle((mWidth * 1.0f) / 2, (mHeight * 1.0f)/ 2, (mWidth * 1.0f) / 2 - strokeWidth, mPaint);
    }

    private float somePaintApi() {
        /**
         * Paint中相关接口
         */
        mPaint.setStyle(Paint.Style.STROKE); // 设置绘制模式: 填充 描边 填充并且描边
        float strokeWidth = 2.0f;
        /**
         * 线条宽度 0 和 1 的区别
         * 默认情况下，线条宽度为 0，但你会发现，这个时候它依然能够画出线，线条的宽度为 1 像素。那么它和线条宽度为 1 有什么区别呢？
         * 其实这个和后面要讲的一个「几何变换」有关：你可以为 Canvas 设置 Matrix 来实现几何变换（如放大、缩小、平移、旋转），在几
         * 何变换之后 Canvas 绘制的内容就会发生相应变化，包括线条也会加粗，例如 2 像素宽度的线条在 Canvas 放大 2 倍后会被以 4 像素
         * 宽度来绘制。而当线条宽度被设置为 0 时，它的宽度就被固定为 1 像素，就算 Canvas 通过几何变换被放大，它也依然会被以 1 像素
         * 宽度来绘制。Google 在文档中把线条宽度为 0 时称作「hairline mode（发际线模式）」
         */
        mPaint.setStrokeWidth(strokeWidth); // 设置描边宽度,单位为像素，默认值是0， 但是会默认固定宽度为1像素
        mPaint.setColor(Color.BLACK); // 设置颜色
        mPaint.setAntiAlias(true); // 开启抗锯齿，可以使图形或者文字的边缘更加平滑，开启抗锯齿的另一种方式是通过初始化画笔时直接设置FLAG  new Paint(Paint.ANTI_ALIAS_FLAG);
        return strokeWidth;
    }

    private void drawColor(Canvas canvas) {
        /**
         * 在整个画布上绘制相应的颜色, 可以覆盖之前绘制的所有内容
         * 作用：可以绘制底色或者在绘制内容上方绘制一个蒙层
         */
        canvas.drawColor(Color.BLUE); // 纯色
        canvas.drawColor(Color.parseColor("#88880000")); // 带透明度的色值
    }
}
