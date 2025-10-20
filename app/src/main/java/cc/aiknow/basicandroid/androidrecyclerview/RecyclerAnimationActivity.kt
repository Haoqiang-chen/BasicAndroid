package cc.aiknow.basicandroid.androidrecyclerview

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import cc.aiknow.basicandroid.R
import java.lang.ref.WeakReference

class RecyclerAnimationActivity : AppCompatActivity() {

    private val dataList = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

    private var task: LoopTask? = null

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_animation)
        // 初始化RecyclerView
        rv = findViewById(R.id.animationRecyclerView)
        // 设置布局管理器
        val layoutManager = SmoothLinearLayoutManager(this)
        rv.layoutManager = layoutManager
        // 设置分割线
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        itemDecoration.setDrawable(this.resources.getDrawable(R.drawable.main_recyclerview_divider))
        rv.addItemDecoration(itemDecoration)
        // 设置适配器
        val adapter = AnimationAdapter()
        rv.adapter = adapter
        // 设置数据源并刷新界面
        adapter.dataList = dataList
        adapter.notifyDataSetChanged()
        task = LoopTask(this)
        Handler(Looper.getMainLooper()).postDelayed(task, 3000)
    }

    /**
     * 循环任务
     */
    private class LoopTask(rv: RecyclerAnimationActivity) : Runnable {
        private val activityRef: WeakReference<RecyclerAnimationActivity> =
            WeakReference(rv)

        override fun run() {
            val activity = activityRef.get() ?: return
            // 不需要循环或者数据为空或者单列不需要循环
            val position = (activity.rv.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()?: 0
            val nextPosition = position + 1
//            (activity.rv.layoutManager as? LinearLayoutManager)?.smoothScrollToPosition(nextPosition, RecyclerView.State.,0)
            activity.rv.smoothScrollToPosition(nextPosition)
            Handler(Looper.getMainLooper()).postDelayed(activity.task, 3000)
        }
    }

    private class AnimationAdapter : RecyclerView.Adapter<AnimationViewHolder>() {

        var dataList: List<String> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):AnimationViewHolder {
            val view = TextView(parent.context).apply {
                layoutParams = RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    230
                )
                gravity = Gravity.CENTER
                background = ColorDrawable(resources.getColor(R.color.colorPrimary))
            }
            return AnimationViewHolder(view)
        }


        override fun getItemCount(): Int {
            return Int.MAX_VALUE
        }

        override fun onBindViewHolder(holder: AnimationViewHolder, position: Int) {
            val curDataIndex = position % dataList.size
            holder.bind(dataList[curDataIndex])
        }
    }

    private class AnimationViewHolder(view: TextView) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view
        fun bind(data: String) {
            textView.text = data
        }
    }

    private class TopLinearSmoothScroller(context: Context?) : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }

    private class SmoothLinearLayoutManager(context: Context?) : LinearLayoutManager(context) {
        override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
            val scroller = TopLinearSmoothScroller(recyclerView?.context)
            scroller.targetPosition = position
            startSmoothScroll(scroller)
        }
    }
}