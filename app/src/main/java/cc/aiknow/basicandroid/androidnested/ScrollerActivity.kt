package cc.aiknow.basicandroid.androidnested

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cc.aiknow.basicandroid.R
import kotlinx.android.synthetic.main.activity_scroller.*

/**
 * @Description: 滑动辅助组件的相关使用Demo
 * @Author: chenhaoqiang
 * @Since: 2023/8/23
 * @Version: 1.0
 */
class ScrollerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroller)
        clickBtn.setOnClickListener { scrollerText.startScroll() }
    }
}