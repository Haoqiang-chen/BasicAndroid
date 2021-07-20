package cc.aiknow.basicandroid.textview;

import android.content.Context;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @Description: 自定义的带便签（后面）的TextView，类似：  xxxxxxx [标签View]
 * @Author chenhaoqiang
 * @Since 2021/7/8
 * @Version 1.0
 */
public class LabeledTextView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {

    private TextView textView;
    private View labelView;
    private LayoutParams textViewLp;
    private String text;

    public LabeledTextView(Context context) {
        this(context, null);
    }

    public LabeledTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabeledTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        textView = new TextView(getContext());
        textViewLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        textView.setMaxLines(2);  // 当设置了最大行数后不起作用
        addView(textView, textViewLp);
    }

    @Override
    public void onGlobalLayout() {
        adjustPosition();
        textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private void adjustPosition() {
        Layout layout = textView.getLayout();
        if (layout == null) {
            return;
        }
        // 文字行数
        int lineCount = layout.getLineCount();
        // 最后一行的文字宽度
        float lastLineWidth = layout.getLineWidth(lineCount - 1);
        // 最后一行最后一个字符的下标
        int lastLineEnd = layout.getLineEnd(lineCount - 1);
        // 如果放不下则直接放到下一行
        if (lastLineWidth + labelView.getWidth() > getWidth() + getPaddingLeft() + getPaddingRight()) {
            LayoutParams layoutParams = (LayoutParams) labelView.getLayoutParams();
            layoutParams.topMargin = layout.getHeight();
            layoutParams.leftMargin = 0;
            labelView.setLayoutParams(layoutParams);
            return;
        }
        int textLineHeight = layout.getHeight() / lineCount;
        int lastLineCharRight = (int) layout.getSecondaryHorizontal(lastLineEnd);
        LayoutParams layoutParams = (LayoutParams) labelView.getLayoutParams();
        layoutParams.topMargin = layout.getHeight() - textView.getPaddingBottom() - textLineHeight / 2 - labelView.getHeight() / 2;
        layoutParams.leftMargin = lastLineCharRight;
        labelView.setLayoutParams(layoutParams);
    }

    /**
     * 可以获取相应宽度能容纳的字符个数
     * @param strictWidth
     * @param maxWidth
     * @return
     */
    private int getLastLineCharNum(int strictWidth, int maxWidth) {
        StaticLayout staticLayout = new StaticLayout(this.text, this.textView.getPaint(), strictWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        StaticLayout staticLayout1 = new StaticLayout(this.text, this.textView.getPaint(), maxWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        return staticLayout1.getLineEnd(0) - staticLayout.getLineEnd(0);
    }

    public void setText(String text) {
        this.text = text;
        textView.setText(this.text);
    }

    public void addLabelView(View labelView) {
        this.labelView = labelView;
        addView(labelView);
        textView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }
}
