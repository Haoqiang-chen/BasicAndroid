package cc.aiknow.basicandroid.androidretrofit;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cc.aiknow.basicandroid.R;

public class RetrofitActivity extends AppCompatActivity {

    private EditText sourceWordText, targetWordText;
    private Button translateButton, translateButtonByLiveData;
    private TranslateRequest translateRequest;
    private String sourceWord;
    private Requesthandler requesthandler;
    private RequestViewModel requestViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        translateRequest = new TranslateRequest();
        requesthandler = new Requesthandler();
        translateRequest.setHandler(requesthandler);
        initView();
        initListener();
        requestViewModel = ViewModelProviders.of(this).get(RequestViewModel.class);
        requestViewModel.getResult().observe(this, requestObserver);
    }

    private void initView() {
        sourceWordText = findViewById(R.id.sourceWordText);
        targetWordText = findViewById(R.id.targetWordText);
        translateButton = findViewById(R.id.translateButton);
        translateButtonByLiveData = findViewById(R.id.translateButtonByLiveData);
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

        translateButtonByLiveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sourceWordText.getText() != null && !sourceWordText.getText().toString().equals("")) {
                    sourceWord = sourceWordText.getText().toString();
                } else {
                    sourceWord = sourceWordText.getHint().toString();
                }
                requestViewModel.requestTranslateRequest(sourceWord);
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

    private Observer<String> requestObserver = new Observer<String>() {
        @Override
        public void onChanged(String targetWord) {
            targetWordText.setText(targetWord);
        }
    };
}
