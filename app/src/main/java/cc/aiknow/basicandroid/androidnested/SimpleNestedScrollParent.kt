package cc.aiknow.basicandroid.androidnested

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat

/**
 * @Description: 自定义支持秦涛滑动的LinearLayout
 * @Author chenhaoqiang
 * @Since 2023/8/10
 * @Version 1.0
 */

const val TAG = "NestedScroll"

class SimpleNestedScrollParent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), NestedScrollingParent3 {

    /**
     * 决定父控件是否接受子控件触发的滑动嵌套操作， 当触发嵌套滑动的子控件触发startNestedScroll方法后，会调用该方法，判断父控件是否要接受嵌套滑动操作
     * @param child 包含Target控件的该父控件的直接子控件
     * @param target 发起嵌套滑动的控件
     * @param axes 滑动方向
     * @param type 嵌套滑动的类型  TYPE_TOUCH  表示用户触摸发生的滑动， TYPE_NON_TOUCH 表示非用户触摸屏幕触发的互动，一般是惯性滑动
     */
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        Log.e(TAG, "onStartNestedScroll ChildView: ${child.javaClass}  TargetView: ${target.javaClass}  TYpe: $type")
        if ((axes and ViewCompat.SCROLL_AXIS_VERTICAL) == ViewCompat.SCROLL_AXIS_VERTICAL) {
            return true
        }
        return false
    }

    /**
     * 当父控件接口滑动操作后（即onStartNestedScroll返回true后）触发该方法，表示父控件接受嵌套滑动操作
     * @param child 包含Target控件的该父控件的直接子控件
     * @param target 发起嵌套滑动的控件
     * @param axes 滑动方向
     * @param type 嵌套滑动的类型  TYPE_TOUCH  表示用户触摸发生的滑动， TYPE_NON_TOUCH 表示非用户触摸屏幕触发的互动，一般是惯性滑动
     */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        Log.e(TAG, "onNestedScrollAccepted ChildView: ${child.javaClass}  TargetView: ${target.javaClass}  TYpe: $type")
    }

    /**
     * 在子控件消费滑动事件之前调用
     * 父控件可以基于该方法判断是否需要在子控件消费之前进行消费数据
     * @param target 发起嵌套滑动的控件
     * @param dx 横向滑动的距离
     * @param dy 纵向滑动的距离
     * @param consumed 该父控件消费的距离，该数组会输出到子控件中
     * @param type 嵌套滑动的类型  TYPE_TOUCH  表示用户触摸发生的滑动， TYPE_NON_TOUCH 表示非用户触摸屏幕触发的互动，一般是惯性滑动
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.e(TAG, "onNestedPreScroll TargetView: ${target.javaClass}  DX: $dx  DY: $dy  消费：x-> ${consumed[0]}  y-> ${consumed[0]}  TYpe: $type")
        // 测试代码，子控件上滑时，父控件先消费数据，自控下下滑时，父控件也先消费事件
        if (dy > 0) {
            if (scrollY <= 300) {
                scrollBy(0, dy)
                consumed[1] = dy
            }
        } else {
            if (scrollY >= 0) {
                scrollBy(0, dy)
                consumed[1] = dy
            }
        }
    }


    /**
     * 在子控件消费完数据之后，回调该方法，将子控件的消费情况告知父控件
     * 父控件可以基于该方法进行消费子控件消费后的事件
     * @param target 发起嵌套滑动的控件
     * @param dxConsumed 子控件横向已经消费的数据
     * @param dyConsumed 子控件纵向已经消费的数据
     * @param dxUnconsumed 子控件横向未消费的像素值
     * @param dyUnconsumed 子控件纵向未消费的像素值
     * @param type 嵌套滑动的类型  TYPE_TOUCH  表示用户触摸发生的滑动， TYPE_NON_TOUCH 表示非用户触摸屏幕触发的互动，一般是惯性滑动
     * @param consumed
     */
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        Log.e(TAG, "onNestedScroll TargetView: ${target.javaClass}  DX: $dxConsumed  DY: $dyConsumed  DX_NOT: $dxUnconsumed  DY_NOT: $dyUnconsumed  消费：x-> ${consumed[0]}  y-> ${consumed[0]}  TYpe: $type")
    }

    /**
     * 同上，只是两个重载方法
     * 在子控件消费完数据之后，回调该方法，将子控件的消费情况告知父控件
     * 父控件可以基于该方法进行消费子控件消费后的事件
     * @param target 发起嵌套滑动的控件
     * @param dxConsumed 子控件横向已经消费的数据
     * @param dyConsumed 子控件纵向已经消费的数据
     * @param dxUnconsumed 子控件横向未消费的像素值
     * @param dyUnconsumed 子控件纵向未消费的像素值
     * @param type 嵌套滑动的类型  TYPE_TOUCH  表示用户触摸发生的滑动， TYPE_NON_TOUCH 表示非用户触摸屏幕触发的互动，一般是惯性滑动
     */
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        Log.e(TAG, "onNestedScroll TargetView: ${target.javaClass}  DX: $dxConsumed  DY: $dyConsumed  DX_NOT: $dxUnconsumed  DY_NOT: $dyUnconsumed  TYpe: $type")
    }

    /**
     * 停止嵌套滑动
     */
    override fun onStopNestedScroll(target: View, type: Int) {
        Log.e(TAG, "onStopNestedScroll TargetView: ${target.javaClass}  TYpe: $type")
    }
}