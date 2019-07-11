package cc.aiknow.basicandroid.androidview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

import cc.aiknow.basicandroid.R;

/**
 * @author chen
 * @version 1.0
 * @since 2019-07-02 22:12
 */
public class RecyclerViewEmptyView extends ConstraintLayout {
    public RecyclerViewEmptyView(Context context) {
        this(context, null);
    }

    public RecyclerViewEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        inflate(context, R.layout.recycler_view_empty, this);
    }
}
