package cc.aiknow.basicandroid.androidnested

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

/**
 * @Description: 解决滑动冲突的ScrollView
 * 缺点是没有处理惯性滑动问题
 * @Author chenhaoqiang
 * @Since 2023/8/3
 * @Version 1.0
 */
class SimpleNestedScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ScrollView(context, attrs) {

    /**
     * 是否是第一次拦截事件
     */
    private var isFirstIntercept = true

    // 当两个ScrollView进行滑动嵌套时会出现内部ScrollView无法响应滑动事件的问题
    // 原因是ScrollView重写了事件拦截方法，外层的ScrollView在事件分发时当检测滑动距离超过阈值时拦截了事件传递，导致子控件接收不到事件
    // 所以可以尝试在外层ScrollView第一次拦截事件时，先不拦截，让内层ScrollView消费事件，内层ScrollView拿到Down事件时同时会限制外层不要拦截事件
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev == null) {
            return false
        }
        if (ev.action == MotionEvent.ACTION_DOWN) {
            isFirstIntercept = true
        }
        val result = super.onInterceptTouchEvent(ev)

        if (result && isFirstIntercept) {
            isFirstIntercept = false
            return false
        }
        return result
    }

    // 如果仅限制外层ScrollView不拦截事件，那么子控件在接受到事件后会阻止外层ScrollView拦截事件，从而导致内层ScrollView滑动到顶部或者底部时
    // 外层ScrollView不会继续滑动。原因是内层的ScrollView在OnTouchEvent方法响应事件时会通过设置标志位阻止外层拦截事件
    // 所以可以考虑在内层控件开始消费事件时，接受到DOWN事件时先阻止外层拦截，当接受MOVE事件时判断是否滑动到顶部或者底部，当不可滑动时允许外层ScrollView拦截事件
    // 从而达到子控件不可滑动时继续滑动后外层响应事件的效果
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev == null) {
            return false
        }
        // 点击的时候阻止外层拦截事件
        if (ev.action == MotionEvent.ACTION_DOWN) {
            parent.requestDisallowInterceptTouchEvent(true)
        }
        // 滑动的时候如果滑动到顶部或者底部这两个不可滑动的状态时，取消对外层不可拦截事件的限制，从而可以试的外层ScrollView拦截事件，响应滑动
        if (ev.action == MotionEvent.ACTION_MOVE) {
            val offsetY = ev.y - getInt("mLastMotionY")
            if ((offsetY > 0 && isScrollToTop()) || (offsetY < 0 && isScrollToBottom())) {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(ev)

    }

    private fun isScrollToTop() = scrollY == 0

    private fun isScrollToBottom(): Boolean {
        return scrollY + height - paddingTop - paddingBottom == getChildAt(0).height
    }


    private fun getInt(valName: String): Int {
        val scrollViewClass = this.javaClass.superclass
        val field = scrollViewClass.getDeclaredField(valName)
        field.isAccessible = true
        return field.getInt(this)
    }
}