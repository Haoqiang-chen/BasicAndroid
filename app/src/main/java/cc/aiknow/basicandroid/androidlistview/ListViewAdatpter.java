package cc.aiknow.basicandroid.androidlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cc.aiknow.basicandroid.R;

/**
 * ListView的适配器
 * 必须重写以下四个方法：
 * getCount:表示适配器表示的数据集的大小
 * getItem：根据该位置对应的数据集中的数据项
 * getItemId：获取该位置上数据项的ID
 * getView：
 * @author chen
 * @version 1.0
 * @since 2019-09-06 16:42
 */
public class ListViewAdatpter extends BaseAdapter {
    private List<Person> dataSet;
    private Context context;

    ListViewAdatpter(List<Person> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context =context;
    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 三个参数分别是
    // view所希望的数据项再数据集中的位置
    // 被重用的View
    // 该view最终被填充的父布局
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ListView的优化
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.itemName);
            viewHolder.des = convertView.findViewById(R.id.itemDes);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(dataSet.get(position).getName());
        viewHolder.des.setText(dataSet.get(position).getDes());
        return convertView;
    }

    // 设计成静态类是为了在Adapter加载时初始化ViewHolder类，该类只需要初始化一次即可
    static class ViewHolder {
        TextView name, des;
    }
}
