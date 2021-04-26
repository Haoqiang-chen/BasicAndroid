package cc.aiknow.basicandroid.textview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import cc.aiknow.basicandroid.R;

public class TextViewActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
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
    }
}