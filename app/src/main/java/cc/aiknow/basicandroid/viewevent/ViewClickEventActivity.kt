package cc.aiknow.basicandroid.viewevent

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import cc.aiknow.basicandroid.R
import kotlinx.android.synthetic.main.activity_view_click_event.*

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
    }

    class CustomView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
    ) : FrameLayout(context, attrs) {

    }
}