package cc.aiknow.basicandroid.androidrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cc.aiknow.basicandroid.R
import kotlinx.android.synthetic.main.activity_recycler_list_adapter.*

class RecyclerListAdapterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_list_adapter)
        val mDataList = mutableListOf<String>()
        for (i in 1 .. 30) {
            mDataList.add("$i")
        }
        val listAdapter = SelfListAdapter()
        listAdapter.submitList(mDataList)
        listAdapter.itemClickCallback = {position ->
            Log.e("chenhaoqiang", "click pos: $position")
            val  newDataList = mutableListOf<String>()
            newDataList.addAll(listAdapter.currentList)
            newDataList.removeAt(position)
            listAdapter.submitList(newDataList)
            listAdapter.notifyDataSetChanged()
        }
        listAdapterRecyclerView.layoutManager = LinearLayoutManager(this)
        listAdapterRecyclerView.adapter = listAdapter

    }
}