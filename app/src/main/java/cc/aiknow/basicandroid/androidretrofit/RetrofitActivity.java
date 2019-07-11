package cc.aiknow.basicandroid.androidretrofit;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cc.aiknow.basicandroid.R;

public class RetrofitActivity extends AppCompatActivity {

    private EditText sourceWordText, targetWordText;
    private Button translateButton;
    private TranslateRequest translateRequest;
    private String sourceWord;
    private Requesthandler requesthandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        translateRequest = new TranslateRequest();
        requesthandler = new Requesthandler();
        translateRequest.setHandler(requesthandler);
        initView();
        initListener();
    }

    private void initView() {
        sourceWordText = findViewById(R.id.sourceWordText);
        targetWordText = findViewById(R.id.targetWordText);
        translateButton = findViewById(R.id.translateButton);
    }

    private void initListener() {
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sourceWordText.getText() != null && !sourceWordText.getText().toString().equals("")) {
                    sourceWord = sourceWordText.getText().toString();
                } else {
                    sourceWord = sourceWordText.getHint().toString();
                }
                translateRequest.requestTranslateResult(sourceWord);
            }
        });
    }

    private class Requesthandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 311) {
                targetWordText.setText(msg.getData().getString("targetWord"));
            }
            if (msg.what == 1995) {
                targetWordText.setText(msg.getData().getString("error"));
            }
            sourceWord = null;
        }
    }
}
