package cc.aiknow.basicandroid.androidview

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cc.aiknow.basicandroid.R
import kotlinx.android.synthetic.main.activity_learn_view.*

/**
 * @Description: 自定义view的学习（应该系统学习）
 * @Author: chen
 * @Date: 2019/6/28
 */
class LearnViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_view)
//        my_draw_view_loop.setOnClickListener {
//            my_draw_view.loop()
//        }

        btn.setOnLongClickListener {
//            ejectionEmojiAnimation.visibility = View.VISIBLE
//            ejectionEmojiAnimation.start()
            val rect = Rect()
            it.getGlobalVisibleRect(rect)
            ejectionAnimationView.visibility = View.VISIBLE
//            ejectionAnimationView.setAnchorPosition((rect.right + rect.left) / 2, (rect.bottom + rect.top) / 2)
            ejectionAnimationView.setAnchorPosition((it.right + it.left) / 2, (it.bottom + it.top) / 2)
            ejectionAnimationView.start()
            true
        }

        btn.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                Log.e("chenhaoqiang", "抬起")
                ejectionAnimationView.stop()
                true
            }
            false
        }
    }
}
