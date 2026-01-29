package cc.aiknow.basicandroid.androidrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cc.aiknow.basicandroid.R
import cc.aiknow.basicandroid.databinding.ActivityRecyclerListAdapterBinding

class RecyclerListAdapterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerListAdapterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerListAdapterBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.listAdapterRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.listAdapterRecyclerView.adapter = listAdapter

    }
}