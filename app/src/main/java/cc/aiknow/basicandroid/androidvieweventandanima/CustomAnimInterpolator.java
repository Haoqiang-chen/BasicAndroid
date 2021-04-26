package cc.aiknow.basicandroid.androidvieweventandanima;


import android.view.animation.Interpolator;

/**
 * 自定义interpolator 尝试贝塞尔曲线
 */
public class CustomAnimInterpolator implements Interpolator {
    private final static int ACCURACY = 4096;
    private int mLastI = 0;
    private float x1, y1, x2, y2;

    @Override
    public float getInterpolation(float input) {

        return 0;
    }

    private double countBezierCurve(float t, float v1, float v2) {
        return (3 * t * (Math.pow((1 - t), 2)) * v1 + 3 * Math.pow(t, 2) * (1 - t) * v2 + Math.pow(t, 3));
    }

}
