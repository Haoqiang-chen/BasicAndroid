package cc.aiknow.basicandroid.androidrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cc.aiknow.basicandroid.R;

/**
 * 显示多种样式的RRecyclerView的适配器
 * 例子在一个RecyclerView显示两种不同的View
 * 方法要点：
 * 1.首先要在放置不同View的RecyclerView的适配器中实现getItemViewType回调函数
 * getItemViewType回调函数：可根据位置信息返回该位置所对应列表项的View类型
 * View类型使用整形来决定，0是默认值
 * 2.然后根据不同的View实现不同的ViewHolder
 * 3.然后在onCreateViewHolder()方法中根据传入的View类型值创建响应的ViewHolder
 * 就相当于创建了不同的View
 * 4.最后需要在onBindViewHolder中根据传入的Holder类型以及位置信息对不同类型的View
 * 进行数据绑定
 * 5.注意：在实现放置不同View的RecyclerView的适配器时应将类型参数设置为ViewHolder
 * 的基类，以便创建不同类型的ViewHolder
 *
 * 更新：添加Header、Footer和EmptyView
 *
 * @author chen
 * @version 1.0
 * @since 2019-07-02 20:06
 */
public class MultipleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = -1;
    private static final int TYPE_EMPTY = -2;

    public static final int TYPE_ONE = 2;
    public static final int TYPE_TWO = 3;

    private List<String> dataBase;
    private Context context;

    private View headerView = null;
    private View footerView = null;
    private View emptyView = null;

    public MultipleRecyclerViewAdapter(Context context, List<String> dataBase) {
        this.context = context;
        this.dataBase = dataBase;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_type_one, parent, false);
            TyprOneViewHolder typrOneViewHolder = new TyprOneViewHolder(rootView);
            return typrOneViewHolder;
        }
        if (viewType == TYPE_TWO) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_type_two, parent, false);
            TypeTwoViewHolder typeTwoViewHolder = new TypeTwoViewHolder(rootView);
            return typeTwoViewHolder;
        }
        if (viewType == TYPE_HEADER) {
            return new RecyclerHFEViewHolder(headerView);
        }
        if (viewType == TYPE_FOOTER) {
            return new RecyclerHFEViewHolder(footerView);
        }
        if (viewType == TYPE_EMPTY) {
            return new RecyclerHFEViewHolder(emptyView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerHFEViewHolder) {
            return;
        }
        int realPosition = position;
        if (headerView != null) {
            realPosition--;
        }
        if (holder instanceof TyprOneViewHolder) {
            ((TyprOneViewHolder) holder).getTextView().setText(dataBase.get(realPosition));
        }
        if (holder instanceof TypeTwoViewHolder) {
            // 设置横向RecyclerView显示的内容
            setHorizontalRecyclerView(((TypeTwoViewHolder) holder).getRecyclerView());
        }
    }

    /**
     * 该回调可返回对应位置的列表项的View类型
     * 默认值为0，即假设适配器只有一个View类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        // 设置RecyclerView中的Header、Footer、Empty
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
        }
        if (footerView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        if (emptyView != null && dataBase.size() == 0) {
            return TYPE_EMPTY;
        }
        // 设置RecyclerView中显示的不同样式的View
        if (headerView != null) {
            position--;
        }
        if (position % 2 == 0) {
                return TYPE_ONE;
        }
        if (position % 2 == 1) {
            return TYPE_TWO;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        int itemCount = dataBase.size();
        if (headerView != null) {
            itemCount += 1;
        }
        if (footerView != null) {
            itemCount += 1;
        }
        if (emptyView != null && dataBase.size() == 0) {
            itemCount += 1;
        }
        return itemCount;
    }

    // 第一种View类型的ViewHolder
    private class TyprOneViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public TyprOneViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.typeOneViewTitle);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    // 第二种类型View的ViewHolder
    private class TypeTwoViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;

        public TypeTwoViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.typeTwoViewRecyclerView);
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }
    }

    // 设置横向RecyclerView中的内容
    private void setHorizontalRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.multiple_recycler_view_hor_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter();
        recyclerView.setAdapter(horizontalRecyclerViewAdapter);

    }

    // 适用于Header、Footer、Empty的ViewHolder
    private class RecyclerHFEViewHolder extends RecyclerView.ViewHolder {
        public RecyclerHFEViewHolder(View itemRootView) {
            super(itemRootView);
        }
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        notifyDataSetChanged();
    }

}
