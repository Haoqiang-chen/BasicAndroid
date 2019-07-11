package cc.aiknow.basicandroid.androidview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;

import cc.aiknow.basicandroid.R;

/**
 * @author chen
 * @version 1.0
 * @since 2019-07-02 22:05
 */
public class RecyclerViewFooterView extends ConstraintLayout {

    public RecyclerViewFooterView(Context context) {
        this(context, null);
    }

    public RecyclerViewFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutParams layoutParams  = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        inflate(context, R.layout.recycler_view_footer, this);
    }

}
