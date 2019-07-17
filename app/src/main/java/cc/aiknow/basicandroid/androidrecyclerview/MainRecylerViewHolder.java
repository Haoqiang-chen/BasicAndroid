package cc.aiknow.basicandroid.androidrecyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import cc.aiknow.basicandroid.R;

/**
 * MianActiviy中RecyclerView的列表项View
 * @author chen
 * @version 1.0
 * @since 2019-06-30 20:59
 */
public class MainRecylerViewHolder extends RecyclerView.ViewHolder {

    private TextView itemDes;

    public MainRecylerViewHolder(View rootView) {
        super(rootView);
        itemDes = rootView.findViewById(R.id.main_recycler_view_item_des);
    }

    public TextView getItemDes() {
        return itemDes;
    }
}
