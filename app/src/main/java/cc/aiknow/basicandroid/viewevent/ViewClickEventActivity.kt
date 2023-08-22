package cc.aiknow.basicandroid.viewevent

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import cc.aiknow.basicandroid.R
import kotlinx.android.synthetic.main.activity_view_click_event.*

/**
 * @Description: 事件分发机制测试 demo
 * 当ViewGroup控件拦截事件后，ViewGroup会调用super.dispatchTouchEvent方法将事件传递给自己进行消费
 * @Author: chenhaoqiang
 * @Since: 2023/8/22
 * @Version: 1.0
 */
class ViewClickEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_click_event)

        view.setOnClickListener {
            Log.e("chenhaoqiang", "执行点击监听")
        }
        view.setOnLongClickListener {
            Log.e("chenhaoqiang", "执行长按监听")
            false
        }
        view.setOnTouchListener { v, event ->
            Log.e("chenhaoqiang", "执行OnTouchListener")
            false
        }
    }

    class CustomView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
    ) : FrameLayout(context, attrs) {

        override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
            Log.e("chenhaoqiang", "执行拦截事件")
            return true
        }

        override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
            Log.e("chenhaoqiang", "执行事件分发")
            return super.dispatchTouchEvent(ev)
        }


        override fun onTouchEvent(event: MotionEvent?): Boolean {
            Log.e("chenhaoqiang", "执行onTouchEvent")
            return super.onTouchEvent(event)
        }
    }
}