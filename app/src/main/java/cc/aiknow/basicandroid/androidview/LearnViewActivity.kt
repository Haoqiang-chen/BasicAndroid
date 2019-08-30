package cc.aiknow.basicandroid.androidview

import android.os.Bundle
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
        my_draw_view_loop.setOnClickListener {
            my_draw_view.loop()
        }
    }
}
