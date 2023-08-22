package cc.aiknow.basicandroid.androidcustomview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Locale;

/**
 * @Description: 绘制基础---文字绘制详解
 * @Author chenhaoqiang
 * @Since 2021/11/19
 * @Version 1.0
 */
public class DrawBaseViewThree extends View {

    private Paint textPaint;

    public DrawBaseViewThree(Context context) {
        this(context, null);
    }

    public DrawBaseViewThree(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawBaseViewThree(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        if (textPaint == null) {
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
//        drawText(canvas);
//        drawTextWithSomeHelpfulMethod(canvas);
        measureTextSize(canvas);
    }

    /**
     * 绘制文字的两个方法
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        /**
         * 牢记文字绘制五条线：top accent baseline decent bottom
         * Canvas.drawText：
         *    方法中y为基线位置， x为绘制的x轴起始位置，需要注意的是 x 值距离实际绘制的文字 直接有一个很小的距离，这个距离是每个字符两边留出的空隙
         *    该方法绘制文字时，文字长度超出画布边界时不会自动折行，文字中的换行符也不会被自动换行，而是会将换行符绘制一个空格
         * canvas.drawTextOnPath  沿着路径绘制文字，当文字长度超出路径长度时，后面的文字会挤在一起绘制
         */
        textPaint.setTextSize(30);
        canvas.drawText("H这个是一个以基线为y值的文\n字绘制方法，很长的一个字符串", 10, 50, textPaint);
        canvas.drawLine(10, 50, 200, 50, textPaint);

        Path path = new Path();
        path.moveTo(50, 150);
        path.lineTo(100,200);
        path.lineTo(200, 150);
        path.lineTo(300, 200);
        Paint pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, pathPaint);
        canvas.drawTextOnPath("这是一个沿着固定路径而绘制的文字", path, 0, 0, textPaint);
    }

    /**
     * 绘制文字时的一些辅助方法
     * @param canvas
     */
    private void drawTextWithSomeHelpfulMethod(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 文字大小  单位像素
        paint.setTextSize(30);
        canvas.drawText("这是一段文字String", 50, 300, paint);


        // 设置字体
        paint.setTypeface(Typeface.DEFAULT_BOLD);  // 粗体
        // 设置地域，绘制的文字会编程相应地域的文字
        paint.setTextLocale(Locale.TAIWAN);
        canvas.drawText("这是一段文字String雨条", 50, 400, paint);


        // 伪粗体:伪粗体是通过程序给描粗实现的，并不是调整字体的weight实现的，具体实现逻辑用的时候再查阅文档
        paint.setFakeBoldText(true);
        // 是否添加删除线
        paint.setStrikeThruText(true);
        // 是否添加下划线,注意这个下划线应该是基于BaseLine画出的
        paint.setUnderlineText(true);
        // 设置文字错切角度，即文字的倾斜角度
        paint.setTextSkewX(-0.5f);
        // 设置文字横向的缩放，即文字的胖瘦
        paint.setTextScaleX(1.5f);
        // 设置字符间距 正值增加间距 负值减少间距
        paint.setLetterSpacing(-0.05f);
        canvas.drawText("这是一段文字String", 50, 500, paint);

        paint = null;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(30);
        // 采用CSS中的font-feature-settings属性值来设置文字样式，感觉知道可以用CSS中方式设置文字样式即可
        paint.setFontFeatureSettings("smcp");
        // 设置文字的对齐方式，对齐的基线是x值（绘制的起始位置），即RIGHT（即x在文字右边） LEFT（x在文字左边）
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("这是一段文字String", 50, 550, paint);
    }

    /**
     * 测量文字尺寸的相关方法
     * @param canvas
     */
    private void measureTextSize(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(30);
        // getFontSpacing 获取推荐的行间距，是两行文字的baseLine之间的距离，这个值可以用于手动换行时确定下一行文字的baseLine值
        // 该值的计算是通过Paint设置的字体、文字大小等自动计算的
        canvas.drawText("获取推荐的行间距：" + paint.getFontSpacing(), 50, 100, paint);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // FontMetrics相关属性，这些值也是根据Paint中设置字体、字号等计算而来
//        fontMetrics.top  // top值 负值
//        fontMetrics.ascent
//        fontMetrics.descent
//        fontMetrics.bottom  // bottom值 正值
//        fontMetrics.leading  // 额外行间距，指的是上一行bottom 到 下一行的top的值

        canvas.drawText("top:" + fontMetrics.top
                        + "ascent:" + fontMetrics.ascent
                        + "descent:" + fontMetrics.descent
                        + "bottom:" + fontMetrics.bottom + "leading:" + fontMetrics.leading, 50, 200, paint);
        Toast.makeText(getContext(), "top:" + fontMetrics.top
                + "ascent:" + fontMetrics.ascent
                + "descent:" + fontMetrics.descent
                + "bottom:" + fontMetrics.bottom + "leading:" + fontMetrics.leading, Toast.LENGTH_LONG).show();

        String str = "测量文字的显示范围";
        Rect rect = new Rect();
        // getTextBounds获取文字的显示范围：其思想是用一个最小矩形将文字包裹起来
        paint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(str, 50, 300, paint);
        rect.left += 50;
        rect.top += 300;
        rect.right += 50;
        rect.bottom += 300;
        canvas.drawRect(rect, paint);

        // 测量文字的宽度measureText
        // 注意measureText得到的值总会比getTextBounds的值要大一些，因为measureText获取的宽度包括字符左右的间隔
        // breakText()方法也可以用来测量文字的宽度，该方法会设置一个宽度，在此宽度的前提下测量文字的宽度，如果文字宽度超出上限，那么会在即将超限的位置截断文字,返回值是截断的文字个数
        float measureText = paint.measureText(str);
        canvas.drawText("测量的宽度为：" + measureText + "Bound宽度:" + (rect.right - rect.left), 50, 400, paint);
    }

}
