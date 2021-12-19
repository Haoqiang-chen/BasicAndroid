package cc.aiknow.basicandroid.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cc.aiknow.basicandroid.R
import cc.aiknow.basicandroid.customview.view.TbHistogramView
import kotlinx.android.synthetic.main.activity_custom_view.*

/**
 * @Description: 学习自定义view的系列课程
 * @Author: chenhaoqiang
 * @Since: 2021/9/30
 * @Version: 1.0
 */
class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)
        val datas = mutableListOf<TbHistogramView.HistogramData>()
        TbHistogramView.HistogramData.setMaxValue(100f)
        datas.add(TbHistogramView.HistogramData("one", 100f))
        datas.add(TbHistogramView.HistogramData("two", 80.0f))
        datas.add(TbHistogramView.HistogramData("three", 90f))
        datas.add(TbHistogramView.HistogramData("four", 10f))
        datas.add(TbHistogramView.HistogramData("five", 20f))
//        histogram_view.setHistogramData(datas)

        progressView.setCurProgress(50f)
    }
}