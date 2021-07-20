package cc.aiknow.basicandroid.androidrecyclerview;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @Description: 万能RecyclerView适配器
 * @Author chenhaoqiang
 * @Since 2021/7/20
 * @Version 1.0
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHoler> {
    /**
     * 数据集
     */
    private final List<T> mDatas;

    public BaseRecyclerViewAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 自定义的布局Id
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 自定义的数据绑定方法
     * @param baseViewHoler 每个数据所对应的ViewHoler
     * @param data 该位置对应的数据
     * @param position 当前位置
     */
    public abstract void bindData(BaseViewHoler baseViewHoler, T data, int position);

    @NonNull
    @Override
    public BaseViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 通过抽象方法实现获取不同Adapter各自定义的Adapter
        return BaseViewHoler.get(parent, getLayoutId());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewAdapter.BaseViewHoler holder, int position) {
        bindData(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 基础ViewHolder
     */
    static class BaseViewHoler extends RecyclerView.ViewHolder {
        /**
         * 存储通过findViewById获取的View，避免频繁调用findViewById方法
         */
        private SparseArray<View> mViews;
        /**
         * 每个ViewHoler所对应的View
         */
        private View mConvertView;

        private BaseViewHoler(View itemView) {
            super(itemView);
            mConvertView = itemView;
            mViews = new SparseArray<>();
        }

        /**
         * 创建ViewHolder
         *
         * @param parent
         * @param layoutId
         * @return
         */
        public static BaseViewHoler get(ViewGroup parent, int layoutId) {
            View convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new BaseViewHoler(convertView);
        }

        /**
         * 获取ItemView中的某一个控件
         *
         * @param viewId
         * @param <T>
         * @return
         */
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }
}
