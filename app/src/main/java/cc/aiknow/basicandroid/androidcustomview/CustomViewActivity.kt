package cc.aiknow.basicandroid.androidcustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cc.aiknow.basicandroid.R
import cc.aiknow.basicandroid.androidcustomview.view.TbHistogramView
import cc.aiknow.basicandroid.databinding.ActivityCustomViewBinding

/**
 * @Description: 学习自定义view的系列课程
 * @Author: chenhaoqiang
 * @Since: 2021/9/30
 * @Version: 1.0
 */
class CustomViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val datas = mutableListOf<TbHistogramView.HistogramData>()
        TbHistogramView.HistogramData.setMaxValue(100f)
        datas.add(TbHistogramView.HistogramData("one", 100f))
        datas.add(TbHistogramView.HistogramData("two", 80.0f))
        datas.add(TbHistogramView.HistogramData("three", 90f))
        datas.add(TbHistogramView.HistogramData("four", 10f))
        datas.add(TbHistogramView.HistogramData("five", 20f))
//        histogram_view.setHistogramData(datas)

        binding.progressView.setCurProgress(50f)
    }
}