package cc.aiknow.basicandroid.androidnested

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cc.aiknow.basicandroid.R
import cc.aiknow.basicandroid.databinding.ActivityScrollerBinding

/**
 * @Description: 滑动辅助组件的相关使用Demo
 * @Author: chenhaoqiang
 * @Since: 2023/8/23
 * @Version: 1.0
 */
class ScrollerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.clickBtn.setOnClickListener { binding.scrollerText.startScroll() }
    }
}