package cc.aiknow.basicandroid.textview;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.LineHeightSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class CustomTextView extends TextView {
    private CustomLineHeightSpan customLineHeightSpan;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 两个参数的setText中BufferType用于描述
     * @param text
     * @param type
     */
    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text == null) {
            return;
        }
        // 通过设置自定义的Span来在计算行高时通过减少Margin实现减少上下padding的目的
        SpannableStringBuilder spannableStringBuilder;
        if (text instanceof  SpannableStringBuilder) {
            spannableStringBuilder = (SpannableStringBuilder) text;
        } else {
            spannableStringBuilder = new SpannableStringBuilder(text);
        }
        if (customLineHeightSpan == null) {
            customLineHeightSpan = new CustomLineHeightSpan(this);
        }
        spannableStringBuilder.setSpan(customLineHeightSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        super.setText(spannableStringBuilder, type);
    }

    private static class CustomLineHeightSpan implements LineHeightSpan {
        private TextView textView;
        private int textSize;
        // 每行文字需要减去的上去Margin
        private int addTopMargin, addBottomMargin;
        // textView的原始LP
        private ViewGroup.LayoutParams textViewLayoutParams;
        // textView的调整后的LP
        private ViewGroup.MarginLayoutParams newTextViewLayoutParams;

        public CustomLineHeightSpan(TextView textView) {
            this.textView = textView;
            this.textSize = (int) textView.getTextSize();
        }

        @Override
        public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int lineHeight, Paint.FontMetricsInt fm) {
            Log.e("chenhaoqiang", "chooseHeight" + textSize);
            // 首先获取原始LP
            if (textView != null) {
                textViewLayoutParams = textView.getLayoutParams();
            }
            // 在原始LP的基础上获取新的LP，以便只更改上下Margin
            if (textViewLayoutParams instanceof ViewGroup.MarginLayoutParams) {
                newTextViewLayoutParams = (ViewGroup.MarginLayoutParams) textViewLayoutParams;
            }
            // 为了适配更改字体大小后避免重复计算的问题，首先将Margin进行恢复，然后获取新的text大小
            if (textView != null && newTextViewLayoutParams != null) {
                newTextViewLayoutParams.topMargin -= addTopMargin;
                newTextViewLayoutParams.bottomMargin -= addBottomMargin;
                textSize = (int) textView.getTextSize();
            }
            if (newTextViewLayoutParams != null) {
                // 根据文字大小计算文字绘制线位置
                changeFontMetricsIntByTextSize(fm, textSize);
                // 通过修改textView的上下Margin来变相实现消除文字上下padding的目标
                addTopMargin = fm.top - fm.ascent;
                addBottomMargin = fm.descent - fm.bottom;
                newTextViewLayoutParams.topMargin += addTopMargin;
                newTextViewLayoutParams.bottomMargin += addBottomMargin;
                textView.setLayoutParams(newTextViewLayoutParams);
            }
        }

        /**
         * 根据设置的文字大小重新调整descent和ascent的值，使得两个值的差值正好为文字大小
         * @param fm
         * @param textSize
         */
        private void changeFontMetricsIntByTextSize(Paint.FontMetricsInt fm, int textSize) {
            int originHeight = fm.descent = fm.ascent;
            if (originHeight <= 0) {
                return;
            }
            float radio = (textSize * 1.0f) / originHeight;
            fm.descent = Math.round(fm.descent * radio);
            fm.ascent = fm.descent - textSize;
        }
    }
}
