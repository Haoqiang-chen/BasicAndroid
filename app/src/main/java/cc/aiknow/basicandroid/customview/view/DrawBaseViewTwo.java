package cc.aiknow.basicandroid.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import java.util.zip.CheckedOutputStream;

import cc.aiknow.basicandroid.R;

/**
 * @Description: 绘制基础---Paint详解
 * @Author chenhaoqiang
 * @Since 2021/10/16
 * @Version 1.0
 */
public class DrawBaseViewTwo extends View {
    private Paint paint;

    public DrawBaseViewTwo(Context context) {
        this(context, null);
    }

    public DrawBaseViewTwo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawBaseViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvasDrawColorLayer(canvas);
//        somePaintMethod(canvas);
//        setShadowLayer(canvas);

//        setMaskFilter(canvas);
        getRealPath(canvas);

    }

    /**
     * 获取Path对应的真实Path
     * 真实Path是包括线宽和PathEffect效果之后实际绘制时的轮廓
     * 使用该方法时注意获取到真实的Path后，绘制的时候Paint的Style设置应该是描边，如果是填充样式就还会把真实Path给填充上
     * @param canvas
     */
    private void getRealPath(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        paint.setStrokeWidth(20);

        path.moveTo(100, 100);
        path.lineTo(200, 300);
        path.lineTo(300, 100);
        path.lineTo(400, 500);
        path.lineTo(500, 400);
        Path dstPath = new Path();
        paint.getFillPath(path, dstPath);
        Paint destPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        destPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(dstPath, destPaint);

        // 文字的真实Path
        String str = "这是一个字符串 This is s str";
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(50);
        canvas.drawText(str, 100, 100, textPaint);
//        paint.getTextPath();
    }

    /**
     * 对即将绘制的整体内容进行遮罩效果过滤
     * @param canvas
     */
    private void setMaskFilter(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        // 模糊遮罩
        // 参数解释： 模糊半径  模糊效果模式(用的时候现查即可)
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL);
        paint.setMaskFilter(blurMaskFilter);
        canvas.drawCircle(200, 200, 100, paint);
        paint.setMaskFilter(null);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200, 200, 100, paint);

        // 浮雕效果遮罩：EmbossMaskFilter  (用的时候现查即可)
    }

    /**
     * 在绘制内容下方绘制一个阴影
     * @param canvas
     */
    private void setShadowLayer(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        /**
         * 参数解释：阴影半径，阴影的上下 和 左右 的偏移量， 阴影色值（当阴影色值有透明度时绘制的阴影透明度就是该色值的透明度，否则会采用Paint中设置的透明度）
         * 在采用硬件加速的情况下，只能绘制文字的阴影，文字之外的必须关闭硬件加速
         */
        paint.setShadowLayer(10, 0, 0, Color.RED);
        paint.setTextSize(30);
        canvas.drawText("这是一个Text", 100, 100, paint);
    }

    /**
     * 一些Paint的相关方法
     * @param canvas
     */
    private void somePaintMethod(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        /**
         * 设置拐角形状
         * MITER： 尖角
         * ROUND： 圆角
         * BEVEL： 平角 (把尖角磨掉)
         * 设置该方法后所有连续绘制的图形的拐角都会有影响
         */
        paint.setStrokeJoin(Paint.Join.MITER);

//        paint.setStrokeMiter(10);  // 设置尖角连接线的最大值，注意当尖角过长时会自动转换成平角
//        canvas.drawRect(100, 100, 400, 400, paint);
        // 下面两条线没有连续绘制,而是绘制两条直线，所以相交处不会被设置拐角形状
//        canvas.drawLine(0, 0, 200, 200, paint);
//        canvas.drawLine(200, 200, 200, 300, paint);

        // 路径中包含两个连续的线
//        Path path = new Path();
//        path.lineTo(150, 150);
//        path.lineTo(150, 50);
//        canvas.drawPath(path, paint);

        /**
         * 是否开启抖动
         * 效果就是在在绘制时有意的添加噪点，从而使图像对于肉眼来说更加真实（效果上就是使得颜色之间的过渡更加柔和）
         * 是指把图像从较高色彩深度（即可用的颜色数）向较低色彩深度的区域绘制时，在图像中有意地插入噪点，通过有规律地扰乱图像来让图像对于肉眼更加真实的做法。
         */
        paint.setDither(true);

        /**
         * 是否采用双线性过滤（这是一种算法）来绘制图像
         * 开启后会使得放大后绘制的图像减少马赛克现象，从而使得颜色之间更加平滑
         */
        paint.setFilterBitmap(true);

        /**
         * PathEffect设置图形轮廓效果，对所有图形有效
         */
        Path path = new Path();
        paint.setStrokeWidth(1);
        path.moveTo(100, 100);
        path.lineTo(200, 300);
        path.lineTo(300, 100);
        path.lineTo(400, 500);
        path.lineTo(500, 400);
//        canvas.drawPath(path, paint);
//        // 圆角轮廓：会将图形轮廓拐角处变成圆形
//        // 参数解释：拐角处圆角半径
//        CornerPathEffect cornerPathEffect = new CornerPathEffect(100);
//        paint.setPathEffect(cornerPathEffect);
//        canvas.drawPath(path, paint);
//
//        // 离散轮廓：会将图形轮廓采用定长的线段进行绘制，并且在绘制时会对线段的角度进行随机偏离
//        // 参数解释：定长的线段，随机偏离量
//        DiscretePathEffect discretePathEffect = new DiscretePathEffect(20, 5);
//        paint.setPathEffect(discretePathEffect);
//        canvas.drawPath(path, paint);

        // 虚线轮廓：采用虚线进行绘制
        // 参数解释：由实线长度、空白线长度组成的数组，以及开始划线时的偏移量（偏移量影响的是第一个实线开始画的时候的偏移位置）
//        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{20, 10, 5, 20}, 10);
//        paint.setPathEffect(dashPathEffect);
//        canvas.drawPath(path, paint);

        // 路径轮廓：采用路径和路径之间作为罗阔
        Path dashPath = new Path();
        path.addCircle(0, 0, 30, Path.Direction.CW);
        PathDashPathEffect pathDashPathEffect = new PathDashPathEffect(dashPath, 50, 0, PathDashPathEffect.Style.ROTATE);
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawPath(path, paint);

        // 组合路径轮廓：SumPathEffect 分别采用两种方式绘制轮廓，会绘制出两个轮廓

        // 结合路径轮廓：ComposePathEffect 分别对路径轮廓采用两种路径，只会绘制一个轮廓


    }

    /**
     * canvas绘制内容时存在三层对颜色的处理逻辑
     * @param canvas
     */
    private void canvasDrawColorLayer(Canvas canvas) {
        /**
         * Canvas绘制内容时有三层对颜色的处理（可以简单理解为 着色 -> 过滤 -> 绘制计算）
         * 第一层：基本颜色，包括画布自己绘制的颜色Canvas.drawColor/ARGB(), 画布绘制图像的颜色Canvas.drawBitmap()，以及Paint中设置的颜色
         * 第二层：Paint中setColorFilter设置颜色过滤，即对即将绘制的颜色像素设置一个统一的过滤规则
         * 第三层：Paint中setXferMode设置源图像如何与目标图像(已经绘制图像)之间如何计算图像色值
         * @param canvas
         */

        paintColor(canvas);

        paintColorFilter(canvas);
        paintXferMode(canvas);
    }

    /**
     * 绘制过程中对颜色的第三层控制
     * 设置绘制时与已存在图像之间的绘制关系
     * @param canvas
     */
    private void paintXferMode(Canvas canvas) {
        /**
         * Paint中可以设置XferMode(X 表示 Trans）
         * XferMode表示的是以当前绘制的内容作为源图像，Canvas上已存在的内容作为目标图像，通过设置PorterDuff Mode来决定如何计算出最终颜色
         * 要想使用 setXfermode() 正常绘制，必须使用离屏缓存 (Off-screen Buffer) 把内容绘制在额外的层上，再把绘制好的内容贴回 View 中
         */
        // saveLayer方法会创建一个透明图层，之后的操作都会在该图层中进行，调用restoreToCount后更新到画布上
        // save方法只是保存了当前Canvas的矩阵
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawRect(0, 0, 100, 100, paint);

        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OVER);  // 相交处目标图像在上遮盖住源图像
        paint.setXfermode(porterDuffXfermode);

        paint.setColor(Color.GREEN);
        canvas.drawRect(50, 0, 150, 100, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }

    private void paintColorFilter(Canvas canvas) {
        /**
         * Paint可以设置颜色像素过滤方法setColorFilter,这些过滤颜色过滤器会在canvas绘制之前对颜色进行一个统一的过滤处理
         * 过滤器包含三种：
         *   第一种：光照过滤器，效果上可以模拟光照的效果，主要思想是通过对除Alpha通道外其他通道的颜像素值进行相乘和添加额外值进行
         *   第二种：PorterDuff过滤器，是通过设置一个源颜色 和一个 PorterDuff mode来实现对目标颜色(要绘制的颜色)进行过滤
         *   第三种：颜色矩阵过滤，通过使用ColorMatrix对颜色进行处理（具体的计算方式到时候现查即可）
         */
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(0xFFFFFF00);  // 红色 + 绿色 = 黄色
//        canvas.drawRect(0,0, 200, 200, paint); // 绘制一个黄色矩形

        /**
         * 光照过滤器LightingColorFilter
         * 参数解释：一个用于相乘的颜色色值(不包含alpha通道)，一个用于相加的颜色色值(不包含alpha通道)
         * 计算规则：对原始颜色中除alpha通道外的其他通道首先先乘以一个对应的色值，然后在额外相加一个色值
         * 计算公式：
         * R' = R * mul.R / 0xff + add.R
         * G' = G * mul.G / 0xff + add.G
         * B' = B * mul.B / 0xff + add.B
         */

//        LightingColorFilter lightingColorFilter = new LightingColorFilter(0x00ffff, 0x000000); // 过滤掉红色，G、B通道保持不变
//        paint.setColorFilter(lightingColorFilter);
//        canvas.drawRect(0,250, 200, 450, paint);  // 绘制过滤后的矩形，是绿色

        /**
         * PorterDuffColorFilter是通过指定一个源颜色色值和PorterDuff模式对与目标绘制对象进行合成
         */
//        paint.setColor(0xFFFF0000);
//        canvas.drawRect(0, 0, 200, 200, paint); // 绘制一个红色矩形
//        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(0xFF00FF00, PorterDuff.Mode.LIGHTEN);  // 设置一个源颜色色值为绿色的 PorterDuff过滤器
//        paint.setColorFilter(porterDuffColorFilter);
//        canvas.drawRect(0, 250, 200, 450, paint);  // 绘制过滤后的矩形，是黄色

        paint.setColor(Color.RED);
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] martix = new float[20];
        martix[0] = 1;
        martix[1] = 2;
        martix[2] = 3;
        martix[3] = 4;
        martix[4] = 5;
        colorMatrix.set(martix);
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixColorFilter);
        canvas.drawRect(0, 250, 200, 450, paint);


    }

    private void paintColor(Canvas canvas) {
        /**
         * Paint中设置颜色有两种方式
         * 第一种：直接使用setColor/或者setARGB来设置颜色色值,注意的是ARGB中A的值表示的是不透明度
         * 第二种：定义着色器Shader，Shader着色器定义的是一个着色规则，当设置了Shader之后paint中设置颜色就不起作用了，此时绘制的内容均采用着色器Shader定义的规则
         *        着色器可以理解为画完图形之后，以着色器定义的规则去给图形着色
         *        Shader的五个子类：线性渐变、辐射渐变、扫描渐变、图像着色、混合着色器
         *
         */

        // 使用set方法
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(0xFFFF0000); // 可以使用想对应的get方法获取Paint中设置的颜色
        canvas.drawLine(0,0, 50, 50, paint);

        paint.setARGB(255, 0, 255, 0);
        canvas.drawLine(0, 0, 50, 100, paint);

        // 使用Shader

        /**
         * 线性渐变LinearGradient
         * 参数解释：线性渐变两个端点的起始坐标、终止坐标，起始颜色、终止颜色，超出端点范围之外内容的着色规则（重复、镜像、CLAMP）
         * 还有一个重载方法，区别是参数列表中可以传递一组颜色，并且可以设置颜色是否均匀使用
         * 端点范围：；两个端点相连后，在端点处的与连接线相垂直的线之间的范围
         */
//        LinearGradient linearGradient = new LinearGradient(100, 100, 600, 600, Color.BLUE, Color.GREEN, Shader.TileMode.REPEAT);
//        paint.setShader(linearGradient);
//        Path path = new Path();
//        RectF rectF = new RectF(100, 100, 600, 600);
//        path.addRect(rectF, Path.Direction.CW);
//        path.lineTo(600, 600);
//        canvas.drawPath(path, paint);
//        canvas.drawRect(50, 50, 650, 650, paint);
//        canvas.drawCircle(350, 350, 300, paint);


        /**
         * 辐射渐变RadialGradient
         * 参数解释：辐射渐变的中心，半径，中心点颜色、圆边颜色，超出范围之外的着色规则
         * 还有一个重载方法，区别是参数列表中可以传递一组颜色，并且可以设置颜色是否均匀使用
         */
//        RadialGradient radialGradient = new RadialGradient(250, 250, 50, Color.BLUE, Color.GREEN, Shader.TileMode.REPEAT);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setShader(radialGradient);
//        canvas.drawCircle(250, 250, 100, paint);
//        canvas.drawRect(250, 250, 400, 400, paint);

        /**
         * 扫描渐变SweepGradient
         * 参数解释：扫描渐变的中心，扫描的起始颜色、终止颜色， 注意扫描的方向是顺时针方向
         * 还有一个重载方法，区别是参数列表中可以传递一组颜色，并且可以设置颜色是否均匀使用
         */
//        SweepGradient sweepGradient = new SweepGradient(250, 250, new int[]{Color.RED, Color.BLUE, Color.GREEN}, new float[]{0.2f, 0.3f, 0.5f});
//        paint.setShader(sweepGradient);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(250, 250, 200, paint);

        /**
         * 图像着色BitmapShader
         * 参数解释：Bitmap图像、横向重复模式、纵向重复模式
         */
//        BitmapShader bitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.drawable_cartoon), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//        paint.setShader(bitmapShader);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(250, 250, 100, paint);
//        canvas.drawRect(250, 250, 400, 400, paint);

        /**
         * 混合着色器ComposeShader
         * 注意混合着色器在硬件加速情况下不支持两个相同类型的Shader
         */
        BitmapShader bitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.jump1), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        BitmapShader bitmapShader1 = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.icon_mask_ad_fun_logo_ks), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        ComposeShader composeShader = new ComposeShader(bitmapShader, bitmapShader1, PorterDuff.Mode.SRC_OVER);
        paint.setShader(composeShader);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(250, 250, 200, paint);


    }
}
