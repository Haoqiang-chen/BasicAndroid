package cc.aiknow.basicandroid.androidrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.aiknow.basicandroid.R;

/**
 * 横向RecyclerView的适配器
 * @author chen
 * @version 1.0
 * @since 2019-07-02 20:39
 */
public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.HorizontalRecyclerViewHolder> {

    private List<String> dataBase;

    HorizontalRecyclerViewAdapter() {
        dataBase = new ArrayList<String>(){{
            add("横向内容1");
            add("横向内容2");
            add("横向内容3");
            add("横向内容4");
            add("横向内容5");
            add("横向内容6");
            add("横向内容7");
            add("横向内容8");
            add("横向内容9");
        }};
    }
    @Override
    public HorizontalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_recyclerview_item, parent, false);
        HorizontalRecyclerViewHolder horizontalRecyclerViewHolder = new HorizontalRecyclerViewHolder(rootView);
        return horizontalRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalRecyclerViewHolder holder, int position) {
        holder.getTextView().setText(dataBase.get(position));
    }

    @Override
    public int getItemCount() {
        return dataBase.size();
    }

    // 横向RecyclerView的ViewHolder
    public class HorizontalRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public HorizontalRecyclerViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.main_recycler_view_item_des);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
