package cc.aiknow.basicandroid.androidviewpager

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import cc.aiknow.basicandroid.R

class ViewPager2Activity : AppCompatActivity() {

    private val viewPager2: ViewPager2 by lazy { findViewById(R.id.view_pager2) }
    private val viewPager2Two: ViewPager2 by lazy { findViewById(R.id.view_pager2_2) }
    private val dataList = listOf(Color.RED, Color.BLUE, Color.GREEN)
    private val handler = object : Handler() {
        override fun handleMessage(msg: android.os.Message) {
            viewPager2Two.setCurrentItem(viewPager2Two.currentItem + 1, true)
            loop()
        }
    }

    fun loop() {
        handler.sendMessageDelayed(Message(), 2000)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2)

        val viewPager2Adapter = ViewPager2Adapter(dataList)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = viewPager2Adapter
        viewPager2.currentItem = 1
        viewPager2.isUserInputEnabled = false
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    viewPager2.setCurrentItem(viewPager2Adapter.itemCount - 2, false)
                } else if (position == viewPager2Adapter.itemCount - 1) {
                    viewPager2.setCurrentItem(1, false)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

            }
        })
//        handler.sendMessage(Message())


        //////////////
        viewPager2Two.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val adapter = ViewPager2Adapter2(dataList)
        viewPager2Two.adapter = adapter
    }

    private class ViewPager2Adapter(private val dataList: List<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = View(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            }
            return ItemViewHolder(view)
        }

        override fun getItemCount(): Int {
            return dataList.size + 2
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ItemViewHolder).bindData(dataList[getRealPosition(position)])
        }

        private fun getRealPosition(position: Int): Int {
            // 如果是第一个虚拟项，返回最后一项
            if (position == 0) return dataList.size - 1
            // 如果是最后一个虚拟项，返回第一项
            if (position == itemCount - 1) return 0
            // 否则返回实际位置
            return position - 1
        }

    }

    private class ViewPager2Adapter2(private val dataList: List<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = View(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            }
            return ItemViewHolder(view)
        }

        override fun getItemCount(): Int {
            return Int.MAX_VALUE
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ItemViewHolder).bindData(dataList[getRealPosition(position)])
        }

        private fun getRealPosition(position: Int): Int {
           return position % dataList.size
        }

    }

    private class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindData(color: Int) {
            itemView.setBackgroundColor(color)
        }
    }
}