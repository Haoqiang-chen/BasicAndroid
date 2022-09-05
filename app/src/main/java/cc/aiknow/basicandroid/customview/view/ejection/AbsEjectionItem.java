package cc.aiknow.basicandroid.customview.view.ejection;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;

import java.util.Random;

/**
 * @Description: 弹射元素抽象类
 * @Author chenhaoqiang
 * @Since 2022/3/31
 * @Version 1.0
 */
public abstract class AbsEjectionItem {
    /**
     * 初始弹射角度
     */
    protected int ejectionDegree;

    /**
     * 锚点位置X坐标
     */
    protected int anchorX;

    /**
     * 锚点位置Y坐标
     */
    protected int anchorY;

    /**
     * 运动起始位置的X坐标
     */
    protected int startX;

    /**
     * 运动起始位置的Y坐标
     */
    protected int startY;

    /**
     * 运动中的x坐标
     */
    protected int x;

    /**
     * 运动中的y坐标
     */
    protected int y;

    /**
     * 图形半径
     */
    protected int radius;

    /**
     * 图形透明度
     */
    protected int alpha = 255;

    /**
     * 透明度衰减步长
     */
    protected float alphaStep = 255 / 100;

    /**
     * 图形运动过的距离
     */
    protected int distance = 0;

    /**
     * 图形
     */
    protected int breakDis = 0;

    /**
     * 运动距离步长
     */
    protected int v = 90;

    /**
     * 运动距离步长衰减因子
     */
    protected int t = 0;

    /**
     * 加速度
     */
    protected float a = -0.4f;

    /**
     * 运动中的竖直方向
     */
    protected Direction directionH = Direction.TOP;


    /**
     * 运动中的水平方向
     */
    protected Direction directionW = Direction.RIGHT;

    /**
     * 绘制区域左侧边界
     */
    protected int leftBoundary;
    /**
     * 绘制区域右侧边界
     */
    protected int rightBoundary;

    /**
     * 绘制区域上侧侧边界
     */
    protected int topBoundary;

    /**
     * 绘制区域底侧边界
     */
    protected int bottomBoundary;

    /**
     * 旋转角度
     */
    protected int rotateDegree = 0;

    /**
     * 绘制paint
     */
    protected Paint paint;

    /**
     * 生命周期状态
     */
    protected LifeCycleState lifeCycleState = LifeCycleState.ACTIVE;

    /**
     * 声明周期计时器
     */
    protected CountDownTimer countDownTimer;

    /**
     * 随机数生成
     */
    protected Random random;

    public AbsEjectionItem() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        random = new Random();
    }

    /**
     * 改变当前状态
     */
    public abstract void changeState();

    /**
     * 绘制
     */
    public abstract void draw(Canvas canvas);
}
