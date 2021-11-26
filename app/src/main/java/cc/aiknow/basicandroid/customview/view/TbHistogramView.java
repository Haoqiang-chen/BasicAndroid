package cc.aiknow.basicandroid.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Random;

import cc.aiknow.basicandroid.R;


/**
 * @Description: 柱状图控件，支持自定义柱状图坐标轴线宽、颜色、
 * @Author chenhaoqiang
 * @Since 2021/10/20
 * @Version 1.0
 */
public class TbHistogramView extends View {

    /**
     * 坐标轴线宽
     */
    private static final int AXIS_LINE_WIDTH = 2;

    /**
     * 坐标轴的宽高与控件宽高的比例
     */
    private static final float AXIS_PROPORTION_OF_VIEW_SIZE = 0.8f;

    /**
     * 坐标轴与控件边缘之间距离的占比
     */
    private static final float AXIS_MARGIN_PROPORTION = 0.1f;

    /**
     * X轴坐标轴箭头与坐标轴长度之间比例
     */
    private static final float ARROW_X_LENGTH_PROPORTION_OF_AXIS = 0.05f;

    /**
     * Y轴坐标轴箭头与坐标轴长度之间比例
     */
    private static final float ARROW_Y_LENGTH_PROPORTION_OF_AXIS = 0.1f;

    /**
     * 箭头与坐标轴之间的角度
     */
    private static final int ARROW_ANGLE_WITH_AXIS = 15;

    /**
     * 矩形宽度占比
     */
    private static final float RECT_WIDTH_PROPORTION = 0.85f;

    /**
     * 随机颜色边界
     */
    private static final int RANDOM_COLOR_BOUND = 0x00FFFFFF;

    /**
     * 随机颜色基础值
     */
    private static final int RANDOM_COLOR_BASE = 0xFF000000;

    /**
     * 默认文字大小
     */
    private static final int TEXT_DEFAULT_SIZE = 20;

    /**
     * 柱状图控件的宽
     */
    private int mWidth;

    /**
     * 柱状图控件的高
     */
    private int mHeight;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 坐标轴线宽
     */
    private int mAxisLineWidth;

    /**
     * 坐标轴颜色
     */
    private int mAxisColor;

    /**
     * X轴长度
     */
    private float mAxisXLength;

    /**
     * Y轴长度
     */
    private float mAxisYLength;

    /**
     * X轴与控件底部之间距离
     */
    private float mAxisXMargin;

    /**
     * Y轴与控件左边之间距离
     */
    private float mAxisYMargin;

    /**
     * X轴箭头长度
     */
    private float mArrowXLength;

    /**
     * X轴箭头在X轴上的投影长度
     */
    private float mArrowXWidth;

    /**
     * Y轴箭头长度
     */
    private float mArrowYLength;

    /**
     * Y轴箭头在Y轴上的投影长度
     */
    private float mArrowYWidth;

    /**
     * 坐标轴路径
     */
    private Path mAxisPath;

    /**
     * 矩形坐标
     */
    private RectF[] rectCoordinates;

    /**
     * 柱状图中对应数据集合
     * key：数据名称
     * value: 数据比例
     */
    private List<HistogramData> mDatas;

    /**
     * 数据最大值
     */
    private float mValueMax;

    /**
     * 矩形颜色，一组色值应与数据集合一一对应
     */
    private List<Integer> mRectColors;


    /**
     * 随机数生成器
     */
    private Random mColorRandom;

    /**
     * 文字字体大小
     */
    private int mTextSize;

    /**
     * 文字字体颜色，默认黑色
     */
    private int mTextColor;


    public TbHistogramView(Context context) {
        this(context, null);
    }

    public TbHistogramView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TbHistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化
        iniView(context, attrs);
        initObject();
    }

    /**
     * 初始化
     *
     * @param context 上下文
     * @param attrs   属性集
     */
    private void iniView(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TbHistogramView);
        mAxisColor = typeArray.getColor(R.styleable.TbHistogramView_axisColor, Color.BLACK);
        mAxisLineWidth = typeArray.getInt(R.styleable.TbHistogramView_axisLineWidth, AXIS_LINE_WIDTH);
        mTextSize = typeArray.getInt(R.styleable.TbHistogramView_textSize, TEXT_DEFAULT_SIZE);
        mTextColor = typeArray.getColor(R.styleable.TbHistogramView_textColor, Color.BLACK);
        typeArray.recycle();
    }

    /**
     * 计算属性值
     */
    private void initVariable() {
        // 计算x轴长度
        mAxisXLength = mWidth * AXIS_PROPORTION_OF_VIEW_SIZE;
        // 计算y轴长度
        mAxisYLength = mHeight * AXIS_PROPORTION_OF_VIEW_SIZE;
        // x轴距离控件底部距离
        mAxisXMargin = mHeight * AXIS_MARGIN_PROPORTION;
        // y轴距离控件左边距离
        mAxisYMargin = mWidth * AXIS_MARGIN_PROPORTION;
        // x轴箭头长度
        mArrowXLength = mAxisXLength * ARROW_X_LENGTH_PROPORTION_OF_AXIS;
        // y轴箭头长度
        mArrowYLength = mAxisYLength * ARROW_Y_LENGTH_PROPORTION_OF_AXIS;
    }

    /**
     * 初始化对象
     */
    private void initObject() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 初始化坐标轴路径
        mAxisPath = new Path();
        mColorRandom = new Random();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取控件的宽高
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 初始化变量
        initVariable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制坐标轴
        drawAxis(canvas);
        // 绘制矩形
        drawAndCalculateRectCoordinate(canvas);
        // 绘制文字
        drawText(canvas);
    }


    /**
     * 设置坐标轴Paint样式
     */
    private void initAxisPaint() {
        if (mPaint != null) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(mAxisColor);
            mPaint.setStrokeWidth(mAxisLineWidth);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    /**
     * 设置矩形Paint样式
     */
    private void initRectPaint() {
        if (mPaint != null) {
            mPaint.setStyle(Paint.Style.FILL);
        }
    }

    /**
     * 设置文字Paint样式
     */
    private void initTextPaint() {
        if (mPaint != null) {
            mPaint.setColor(mTextColor);
            mPaint.setTextSize(mTextSize);
        }
    }

    /**
     * 绘制坐标轴
     *
     * @param canvas 画布
     */
    private void drawAxis(Canvas canvas) {
        initAxisPaint();

        // 首先移动到y轴上顶点
        mAxisPath.moveTo(mAxisXMargin, mAxisYMargin);
        // 添加y轴：路劲延续到y轴下定点，即坐标轴原点
        mAxisPath.lineTo(mAxisXMargin, mAxisXMargin + mAxisYLength);
        // 添加x轴：路径由远点延伸至x轴右顶点
        mAxisPath.lineTo(mAxisYMargin + mAxisXLength, mAxisXMargin + mAxisYLength);

        // 添加x轴箭头
        float arrowXWidth = (float) (mArrowXLength * Math.cos(Math.toRadians(ARROW_ANGLE_WITH_AXIS)));
        mArrowXWidth = arrowXWidth;
        float arrowXHeight = (float) (mArrowXLength * Math.sin(Math.toRadians(ARROW_ANGLE_WITH_AXIS)));
        mAxisPath.moveTo(mAxisYMargin + mAxisXLength, mAxisXMargin + mAxisYLength);
        mAxisPath.rLineTo(-arrowXWidth, -arrowXHeight);
        mAxisPath.moveTo(mAxisYMargin + mAxisXLength, mAxisXMargin + mAxisYLength);
        mAxisPath.rLineTo(-arrowXWidth, arrowXHeight);

        // 添加y轴箭头
        mAxisPath.moveTo(mAxisXMargin, mAxisYMargin);
        float arrowYWidth = (float) (mArrowYLength * Math.cos(Math.toRadians(ARROW_ANGLE_WITH_AXIS)));
        mArrowYWidth = arrowYWidth;
        float arrowYHeight = (float) (mArrowYLength * Math.sin(Math.toRadians(ARROW_ANGLE_WITH_AXIS)));
        mAxisPath.rLineTo(-arrowYHeight, arrowYWidth);
        mAxisPath.moveTo(mAxisXMargin, mAxisYMargin);
        mAxisPath.rLineTo(arrowYHeight, arrowYWidth);
        canvas.drawPath(mAxisPath, mPaint);
    }


    /**
     * 计算矩形坐标并绘制矩形
     *
     * @param canvas 画布
     */
    private void drawAndCalculateRectCoordinate(Canvas canvas) {
        int dataNum;
        if (mDatas != null && mDatas.size() > 0) {
            dataNum = mDatas.size();
        } else {
            return;
        }

        // 初始化绘制矩形的Paint
        initRectPaint();

        // 首先计算出每个矩形宽度
        float xAxisCanUsedLength = mAxisXLength - mArrowXWidth * 2 - (AXIS_LINE_WIDTH * 1.0f) / 2;
        float singleRectAndSpaceLength = xAxisCanUsedLength / dataNum;
        float rectWidth = singleRectAndSpaceLength * RECT_WIDTH_PROPORTION;
        // 第一个矩形左下角坐标X值
        float curRectLeftBottomX = mAxisYMargin + singleRectAndSpaceLength - rectWidth;
        // x 轴的Y坐标
        float xAxis = mAxisXMargin + mAxisYLength - (AXIS_LINE_WIDTH * 1.0f) / 2;
        // 分别计算每个矩形坐标并绘制
        for (int i = 0; i < dataNum; i++) {
            float rectHeight = (mAxisYLength - mArrowYWidth - (AXIS_LINE_WIDTH * 1.0f) / 2) * (mDatas.get(i).mValue / mValueMax);
            float curRectLeftTopY = xAxis - rectHeight;
            RectF rectF = new RectF(curRectLeftBottomX, curRectLeftTopY, curRectLeftBottomX + rectWidth, xAxis);
            curRectLeftBottomX += singleRectAndSpaceLength;
            int curRectColor;
            if (mRectColors != null) {
                curRectColor = mRectColors.get(i);
            } else {
                curRectColor = RANDOM_COLOR_BASE | mColorRandom.nextInt(RANDOM_COLOR_BOUND);
            }
            mPaint.setColor(curRectColor);
            // 采用离屏缓存进行绘制
            int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawRoundRect(rectF, 20, 20, mPaint);
            PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
            mPaint.setXfermode(porterDuffXfermode);
            RectF maskRect = new RectF(rectF.left, rectF.top + rectHeight / 2, rectF.right, rectF.bottom);
            canvas.drawRect(maskRect, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(saved);
            rectCoordinates[i] = rectF;
        }
    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        int dataNum;
        if (mDatas != null && mDatas.size() > 0) {
            dataNum = mDatas.size();
        } else {
            return;
        }

        // 初始化绘制文字的Paint
        initTextPaint();

        // 绘制文字
        float rectWidth = rectCoordinates[0].right - rectCoordinates[0].left;
        for (int i = 0; i < dataNum; i++) {
            // 绘制数值
            float curValueWidth = mPaint.measureText(String.valueOf(mDatas.get(i).mValue));
            float offset = (curValueWidth - rectWidth) / 2;
            canvas.drawText(String.valueOf(mDatas.get(i).mValue), rectCoordinates[i].left - offset, rectCoordinates[i].top - 10, mPaint);
            // 绘制名称
            float curNameWidth = mPaint.measureText(String.valueOf(mDatas.get(i).mName));
            offset = (curNameWidth - rectWidth) / 2;
            canvas.drawText(mDatas.get(i).mName, rectCoordinates[i].left - offset, rectCoordinates[i].bottom + 10 + mTextSize, mPaint);
        }
    }


    /**
     * 设置数据
     *
     * @param mDatas HistogramData数据列表
     */
    public void setHistogramData(List<HistogramData> mDatas) throws Exception {
        if (mDatas == null || mDatas.size() == 0) {
            throw new Exception("请检查数据合法性");
        }
        this.mDatas = mDatas;
        this.mValueMax = HistogramData.mMaxValue;
        rectCoordinates = new RectF[mDatas.size()];
        postInvalidate();
    }

    /**
     * 设置文字字体大小
     *
     * @param mTextSize 字体大小
     */
    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        postInvalidate();
    }

    /**
     * 设置文字字体颜色
     *
     * @param mTextColor 颜色色值
     */
    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        postInvalidate();
    }

    /**
     * 设置坐标轴颜色
     *
     * @param mAxisColor 颜色色值
     */
    public void setAxisColor(int mAxisColor) {
        this.mAxisColor = mAxisColor;
        postInvalidate();
    }

    /**
     * 设置坐标轴线宽
     *
     * @param mAxisLineWidth 坐标轴线宽
     */
    public void setAxisLineWidth(int mAxisLineWidth) {
        this.mAxisLineWidth = mAxisLineWidth;
        postInvalidate();
    }

    /**
     * 设置矩形颜色
     *
     * @param mRectColors
     */
    public void setRectColors(List<Integer> mRectColors) throws Exception{
        // 检测数据合法性
        if (mDatas == null || mDatas.size() != mRectColors.size()) {
            throw new Exception("请检查数据合法性");
        }
        this.mRectColors = mRectColors;
        postInvalidate();
    }

    /**
     * 柱状图View对应的数据类
     */
    public static class HistogramData {
        /**
         * 数据的最大值常量
         */
        public static final float MAX_VALUE = 100.0f;

        /**
         * 数据的最大值
         */
        private static float mMaxValue = MAX_VALUE;

        /**
         * 数据项名称
         */
        private String mName;

        /**
         * 数据项数值
         */
        private float mValue;

        /**
         * 初始化数据
         *
         * @param name  非空字符串
         * @param value 数值，应大于0小于最大值{@link #mValueMax}
         */
        public HistogramData(@NonNull String name, float value) {
            this.mName = name;
            this.mValue = value;
        }

        public String getName() {
            return mName;
        }

        public float getValue() {
            return mValue;
        }

        public void setName(String name) {
            this.mName = name;
        }

        public static void setMaxValue(float mMaxValue) {
            HistogramData.mMaxValue = mMaxValue;
        }
    }
}
