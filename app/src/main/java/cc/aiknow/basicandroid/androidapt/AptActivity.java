package cc.aiknow.basicandroid.androidapt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.apt_annotation.BindView;
import com.example.apt_library.BindViewTools;

import cc.aiknow.basicandroid.R;

public class AptActivity extends AppCompatActivity {
    @BindView(R.id.text_one)
    TextView textOne;
    @BindView(R.id.text_two)
    TextView textTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt);
        BindViewTools.Bind(this);
        textOne.setText("哈哈哈");
        textTwo.setText("哈哈哈哈哈哈");
    }
}