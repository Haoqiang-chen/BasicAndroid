package cc.aiknow.basicandroid.textview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.aiknow.basicandroid.R;

public class TextViewActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private LabeledTextView labeledTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        initView();
    }

    private void initView() {
        editText = findViewById(R.id.edit_text);
        /**
         * 对文字使用标记的三个步骤：
         * 1.构造字符串
         * 2.构造想要的标记(Span)
         * 3.通过setSpan方法将标价(Span)应用到指定范围
         */
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append("0123456789");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.bule));
        spannableStringBuilder.setSpan(foregroundColorSpan, 1, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        editText.setText(spannableStringBuilder);

        /**
         * 测试自定义的TextView
         */
        textView = findViewById(R.id.custom_text);
        textView.setTextSize(21);
        textView.setText("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");

        labeledTextView = findViewById(R.id.labeled_text);
        labeledTextView.setText("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");

        ImageView imageView = new ImageView(this);
        imageView.setBackground( ContextCompat.getDrawable(this, R.drawable.icon_mask_ad_fun_logo_ks));
        imageView.setLayoutParams(new FrameLayout.LayoutParams(50, 50));

        TextView text = new TextView(this);
        text.setText("广告");

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(imageView);
        linearLayout.addView(text, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundColor(getResources().getColor(R.color.bule));
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        labeledTextView.addLabelView(linearLayout);
    }
}