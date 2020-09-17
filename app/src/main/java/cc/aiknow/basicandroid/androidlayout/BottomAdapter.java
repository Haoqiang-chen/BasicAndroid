package cc.aiknow.basicandroid.androidlayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> dataBase;
    private Context context;

    BottomAdapter(Context context, List<String> dataBase) {
        this.context = context;
        this.dataBase = dataBase;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        BottomViewHolder bottomViewHolder = new BottomViewHolder(textView);
        return bottomViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  BottomViewHolder) {
            ((BottomViewHolder) holder).getTextView().setText(dataBase.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataBase.size();
    }

    private class BottomViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public BottomViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
