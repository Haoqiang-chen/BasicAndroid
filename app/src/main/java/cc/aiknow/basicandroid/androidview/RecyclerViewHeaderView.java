package cc.aiknow.basicandroid.androidview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import cc.aiknow.basicandroid.R;

/**
 * @author chen
 * @version 1.0
 * @since 2019-07-02 21:52
 */
public class RecyclerViewHeaderView extends ConstraintLayout {

    public RecyclerViewHeaderView(Context context) {
        this(context, null);
    }

    public RecyclerViewHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.recycler_view_header, this);
    }
}
