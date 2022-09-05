package cc.aiknow.basicandroid.customview.view.ejection;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * @Description: 弹射表情
 * @Author chenhaoqiang
 * @Since 2022/3/31
 * @Version 1.0
 */
public class EjectionEmotion extends AbsEjectionItem {

    private int ddd = 1;

    /**
     * 半径
     */
    public static final int RADIUS = 90;

    /**
     * 随机发射角度范围
     */
    public static final int EJECTION_DEGREE_RANDOM_BOUND = 180;

    /**
     * 生命周期时间
     */
    public static final int LIFECYCLE_DURATION = 3000;

    /**
     * 生命周期倒计时间隔
     */
    public static final int COUNT_DOWN_INTERVAL = 10;

    /**
     * 透明度变化时间点
     */
    public static final int ALPHA_COUNT_DOWN_START = 2000;

    /**
     * 表情图片的一半长度
     */
    public static final float EMOJI_HALF_BOUND_LENGTH = 82.5f;

    private Bitmap emotionBitmap;

    public EjectionEmotion(Bitmap emotionBitmap, int anchorX, int anchorY, int viewW, int viewH) {
        super();
        this.emotionBitmap = emotionBitmap;
        super.anchorX = anchorX;
        super.anchorY = anchorY;
        startX = anchorX;
        startY = anchorY;
        x = startX;
        y = startY;
        // 设置随机发射角度
        super.ejectionDegree = random.nextInt(EJECTION_DEGREE_RANDOM_BOUND);
        // 根据发射角度设置水平方向
        if (super.ejectionDegree < 90) {
            super.ejectionDegree = 45;
            directionW = Direction.RIGHT;
        } else {
            directionW = Direction.LEFT;
            super.ejectionDegree = 120;
            super.ejectionDegree = 180 - super.ejectionDegree;
        }
        // 初始化半径
        radius = (int) (Math.sqrt((Math.pow(emotionBitmap.getWidth(), 2) + Math.pow(emotionBitmap.getHeight(), 2))) / 2);
//        radius = 50;
        // 初始化绘制边界
        leftBoundary = radius;
        rightBoundary = viewW - radius;
        topBoundary = radius;
        bottomBoundary = viewH - radius;
        // 设置生命周期计时器
        countDownTimer = new CountDownTimer(LIFECYCLE_DURATION, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 当2s后开始进行透明度渐变
                if (millisUntilFinished <= ALPHA_COUNT_DOWN_START) {
                    alpha -= alphaStep;
                }
                rotateDegree += 10;
                if (rotateDegree > 360) {
                    rotateDegree = 0;
                }
            }

            @Override
            public void onFinish() {
                lifeCycleState = LifeCycleState.DEAD;
                countDownTimer.cancel();
            }
        };
        // 初始化后立刻开始计时
        countDownTimer.start();
    }

    @Override
    public void changeState() {
        if (ddd < 2) {
            ddd++;
            return;
        }
        t += 1;
        distance = (int) (v * t + (a * Math.pow(t, 2)) / 2);
        // 向上运动
        if (directionH == Direction.TOP) {
            // 向右运动
            if (directionW == Direction.RIGHT) {
                // 计算当前x坐标位置
                x = (int) ((distance - breakDis) * Math.cos(Math.toRadians(ejectionDegree))) + startX;
                y = startY - (int) ((distance - breakDis) * Math.sin(Math.toRadians(ejectionDegree)));
                // x 超过屏幕右侧位置
                if (x >= rightBoundary) {
                    // 纠正dis
                    y = startY - (int)((rightBoundary - startX) * Math.tan(Math.toRadians(ejectionDegree)));
                    // 纠正dis
                    breakDis = distance;
                    directionW = Direction.LEFT;
                    startX = rightBoundary;
                    startY = y;
                    x = rightBoundary;
                }
                // y 超过屏幕上侧，方向改变
                if (y <= topBoundary) {
                    // 纠正dis
                    x = startX + (int)((startY - topBoundary) / Math.tan(Math.toRadians(ejectionDegree)));
                    // 纠正dis
                    directionH = Direction.BOTTOM;
                    startX = x;
                    startY = topBoundary;
                    y = topBoundary;
                    breakDis = distance;
                }
            } else {
                // 向左运动
                x = startX - (int) ((distance - breakDis) * Math.cos(Math.toRadians(ejectionDegree)));
                y = startY - (int) ((distance - breakDis) * Math.sin(Math.toRadians(ejectionDegree)));
                // x超过屏幕左侧
                if (x <= leftBoundary) {
                    // 纠正dis
                    y = startY - (int)((startX - leftBoundary) * Math.tan(Math.toRadians(ejectionDegree)));
                    // 纠正dis
                    directionW = Direction.RIGHT;
                    startX = leftBoundary;
                    startY = y;
                    x = leftBoundary;
                    breakDis = distance;
                }
                // y 超过屏幕上侧，方向改变
                if (y <= topBoundary) {
                    // 纠正dis
                    x = startX - (int)((startY - topBoundary) / Math.tan(Math.toRadians(ejectionDegree)));
                    // 纠正dis
                    directionH = Direction.BOTTOM;
                    startY = topBoundary;
                    startX = x;
                    y = topBoundary;
                    breakDis = distance;
                }
            }
        } else {
            // 向下运动
            if (directionW == Direction.RIGHT) {
                x = (int) ((distance - breakDis) * Math.cos(Math.toRadians(ejectionDegree))) + startX;
                y = startY + (int) ((distance - breakDis) * Math.sin(Math.toRadians(ejectionDegree)));
                // x 超过屏幕右侧位置
                if (x >= rightBoundary) {
                    // 纠正dis
                    y = startY + (int)((rightBoundary - startX) * Math.tan(Math.toRadians(ejectionDegree)));
                    // 纠正dis
                    directionW = Direction.LEFT;
                    startX = rightBoundary;
                    startY = y;
                    x = rightBoundary;
                    breakDis = distance;
                }

                // y 超过屏幕底侧，方向改变
                if (y >= bottomBoundary) {
                    // 纠正dis
                    x = startX + (int)((bottomBoundary - startY) / Math.tan(Math.toRadians(ejectionDegree)));
                    // 纠正dis
                    directionH = Direction.TOP;
                    startY = bottomBoundary;
                    startX = x;
                    y = bottomBoundary;
                    breakDis = distance;
                }
            } else {
                // 向左
                x = startX - (int) ((distance - breakDis) * Math.cos(Math.toRadians(ejectionDegree)));
                y = startY + (int) ((distance - breakDis) * Math.sin(Math.toRadians(ejectionDegree)));
                if (x <= leftBoundary) {
                    // 纠正dis
                    y = startY + (int) ((startX - leftBoundary) * Math.tan(Math.toRadians(ejectionDegree)));
                    // 纠正dis
                    directionW = Direction.RIGHT;
                    startX = leftBoundary;
                    startY = y;
                    x = leftBoundary;
                    breakDis = distance;
                }
                // y 超过屏幕底侧，方向改变
                if (y >= bottomBoundary) {
                    // 纠正dis
                    x = startX - (int)((bottomBoundary - startY) / Math.tan(Math.toRadians(ejectionDegree)));
                    // 纠正dis
                    directionH = Direction.TOP;
                    startY = bottomBoundary;
                    startX = x;
                    y = bottomBoundary;
                    breakDis = distance;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // alpha边界处理
        if (alpha < 0) {
            alpha = 0;
        }
        paint.setAlpha(alpha);
        // 绘制带旋转角度的bitmap
        canvas.save();
        canvas.rotate(rotateDegree, x, y);
        canvas.drawBitmap(emotionBitmap, x - emotionBitmap.getWidth() / 2f, y - emotionBitmap.getHeight() / 2f, paint);
        canvas.restore();
    }
}
