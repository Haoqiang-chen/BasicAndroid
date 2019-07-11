package cc.aiknow.basicandroid.androidrecyclerview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

/**
 * R实现ecyclerView中列表项拖拽、滑动事件
 * 方法：ItemTouchHelper.Callback
 * 1.首先实现ItemTouchHelper.Callback
 * 2.在getMoveFlags中通过makeMovementFlags设置拖拽和滑动方向
 * 3.在move方法中实现拖拽逻辑
 * 4.在swipe方法中实现滑动逻辑
 * 5.将RecyclerView绑定到持有ItemTouchHelper.Callback的ItemTouchHelper
 *
 * @author chen
 * @version 1.0
 * @since 2019-07-03 22:09
 */
public class MainRecyclerViewItemTouchHelper extends ItemTouchHelper.Callback {
    // 设置拖拽、滑动方向

    private List<String> itemNames;
    private MainRecyclerViewAdapter adapter;

    public MainRecyclerViewItemTouchHelper(MainRecyclerViewAdapter adapter, List<String> itemNames) {
        this.adapter = adapter;
        this.itemNames = itemNames;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int frome = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(itemNames, frome, to);
        adapter.notifyItemMoved(frome, to);
        // 在Move时通知move范围内的所有view重新绑定以解决position错乱问题
        // 通过实现接口也可以解决 Adapter中实现接口，此处调用
        adapter.notifyItemRangeChanged(Math.min(frome, to), Math.abs(frome - to) + 1);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int deletePosition = viewHolder.getAdapterPosition();
        itemNames.remove(deletePosition);
        adapter.notifyItemRemoved(deletePosition);
        adapter.notifyDataSetChanged();
    }

    /**
     * 当被选中的ViewHolder发生拖拽或者滑动时被回调
     * 必须调用super方法
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#AAAAAA"));
        }
    }

    // 当一个列表项与用户交互完成且动画完成后被ItemTouchHelper调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0xffeeeeee);
    }
}
