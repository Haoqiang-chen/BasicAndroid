package cc.aiknow.basicandroid.androidrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cc.aiknow.basicandroid.R

/**
 * @Description: ListAdapter内部实现处理元素的添加和删除而无需重绘视图，甚至可以为变化添加动画效果
 * @Author chenhaoqiang
 * @Since 2021/7/20
 * @Version 1.0
 */
class SelfListAdapter : ListAdapter<String, SelfListAdapter.SelfViewHolder>(FlowerDiffCallback) {

    lateinit var itemClickCallback: (Int) -> Unit

    /**
     * DiffUtil: 用于比较数据是否相同
     */
    object FlowerDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    class SelfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.listAdapterText)

        fun bindData(text: String) {
            textView?.apply {
                setText(text)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelfViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_adapter_recycler_item, parent, false)
        return SelfViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelfViewHolder, position: Int) {
        holder.bindData(getItem(position))
        holder.itemView.setOnClickListener {
            itemClickCallback(position)
        }
    }

    interface ItemCLick {
        fun onCLick(position: Int)
    }
}