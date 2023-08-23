package cc.aiknow.basicandroid.androidnested

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import androidx.appcompat.widget.AppCompatTextView

/**
 * @Description: 支持滚动的 TextView
 * @Author chenhaoqiang
 * @Since 2023/8/23
 * @Version 1.0
 */

const val TAG_SCROLLER = "SCROLLER"

class ScrollerTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    private val scroller = Scroller(getContext(), LinearInterpolator())
    private var start = false

    /**
     * 支持滚动文本
     */
    fun startScroll() {
        // X 方向从0 计算到 文本宽度
        scroller.startScroll(0, top, width, 0, 1000)
        start = true
        invalidate()
    }

    override fun computeScroll() {
        if (!start) {
            return
        }
        if (scroller.computeScrollOffset()) {
            // 如果当前X滑动方向为小于0，则说明内容是从控件右边向左滑动
            if(scrollX < 0) {
                // 由于 Scroller 的值始终为 0 到 控件宽度，所以在从右边向左滑动过程中需要加上负的宽度
                scrollTo(-width + scroller.currX, 0)
                // 当滑动 X 值大于 -10 时表示从右边的滑动即将滑动到头，此时可以表示结束，然后重新开始计时
                if (scrollX >= -5) {
                    scrollTo(0, 0 )
                    scroller.forceFinished(true)
                    scroller.startScroll(0, top, width, 0, 1000)
                }
            } else {
                scrollTo(scroller.currX, 0)
                if (scrollX >= width - 5) {
                    scrollTo(-width, 0 )
                    scroller.forceFinished(true)
                    scroller.startScroll(0, top, width, 0, 1000)
                }
            }
            invalidate()
        }
    }
}