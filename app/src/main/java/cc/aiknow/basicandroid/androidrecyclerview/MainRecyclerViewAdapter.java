package cc.aiknow.basicandroid.androidrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import cc.aiknow.basicandroid.R;

/**
 * MianActiviy中RecyclerView的适配器用于创建ViewHolder以及绑定数据等操作
 * @author chen
 * @version 1.0
 * @since 2019-06-30 21:11
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecylerViewHolder> {
    private Map<String, Class<?>> itemDataBase;
    private List<String> itemsNames;
    private Context context;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public void setData(Map<String, Class<?>> itemDataBase, List<String> itemsNames, Context context) {
        this.itemDataBase = itemDataBase;
        this.itemsNames = itemsNames;
        this.context = context;
        this.recyclerViewItemClickListener = (RecyclerViewItemClickListener)context;
    }

    /**
     * 根据布局创建ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MainRecylerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_recyclerview_item, parent, false);
        MainRecylerViewHolder mainRecylerViewHolder = new MainRecylerViewHolder(rootView);
        return mainRecylerViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MainRecylerViewHolder holder, final int position) {
        holder.getItemDes().setText(itemsNames.get(position));
        holder.getItemDes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClickListener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsNames.size();
    }

}
