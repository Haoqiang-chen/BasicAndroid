package cc.aiknow.basicandroid.androidcustomview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Description: 绘制基础---绘制顺序
 * 1. 直接继承View实现自定义View时：重写onDraw时，自定义的绘制内容放在super.onDraw之前或者之后都没有关系，因为super.onDraw()是个空实现
 * 2. 直接继承现在控件时：重写onDraw时，（a）自定义绘制代码在super.onDraw方法调用之前，则自定义绘制内容会被遮住,例如：在图片ImageView上绘制一些图片信息
 *                                   (b)如果在super.onDraw()方法之后，则自定义绘制内容显示在上层，例如：实现一个带背景色的TextView
 * 3
 * @Author chenhaoqiang
 * @Since 2021/12/21
 * @Version 1.0
 */
public class DrawBaseViewFive extends View {

    public DrawBaseViewFive(Context context) {
        this(context, null);
    }

    public DrawBaseViewFive(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawBaseViewFive(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    /**
     * draw()方法中的绘制流程： 可以总结：背景->自己->子view->阴影->前景
     *
     * 1. Draw the background   =>  drawBackground(canvas) 是个私有方法，只能通过属性android:background 属性以及 Java 代码的 View.setBackgroundXxx()设置
     *
     * 2. If necessary, save the canvas' layers to prepare for fading (阴影)  => 根据是否需要绘制阴影，来保存Canvas状态
     *
     * 3. Draw view's content =>  onDraw(canvas)  空实现，需要子类实现
     *
     * 4. Draw children  => dispatchDraw()  空实现，需要子类实现
     *
     * 5. If necessary, draw the fading edges and restore layers  => 有需要的话绘制阴影，然后绘制Canvas状态
     *
     * 6. Draw decorations (scrollbars for instance)  =>  onDrawForeground(canvas) 绘制装饰以及滚动条
     *    前景可以通过 xml 的 android:foreground 属性或 Java 代码的 View.setForeground() 方法来设置，或者通过重写onDrawForeground将自定义的前景绘制代码放在super后面
     * @param canvas
     */

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * View中的onDraw()方法是空方法
         */
        super.onDraw(canvas);

    }
}
