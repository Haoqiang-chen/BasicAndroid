package cc.aiknow.basicandroid.androidstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import cc.aiknow.basicandroid.R;

public class StoreActivity extends AppCompatActivity {

    private EditText sourceText;
    private Button storeButton, getDataButton;
    private TextView showDataText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        initView();
        initListener();
    }

    private void initView() {
        sourceText = findViewById(R.id.sourceText);
        storeButton = findViewById(R.id.storeButton);
        getDataButton = findViewById(R.id.getDataButton);
        showDataText = findViewById(R.id.showDataText);
    }

    private void initListener() {
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text;
                if (sourceText.getText() != null && !sourceText.getText().toString().equals("") ) {
                    text = sourceText.getText().toString();
                } else {
                    text = sourceText.getHint().toString();
                }
                saveTextToFile(text);
            }
        });

        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataText.setText(getTextFromFile());
            }
        });

    }

    private void saveTextToFile(String text) {
        try {
            // 打开当前程序包相关联的指定文件名的文件，若不存在则创建
            FileOutputStream fileOutputStream = openFileOutput("InternalStorage.txt", MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.append(text);
            outputStreamWriter.close();
            outputStreamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTextFromFile() {
        StringBuilder text = new StringBuilder();
        try {
            FileInputStream fileInputStream = openFileInput("InternalStorage.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            if (inputStreamReader.ready()) {
                char[] textChar = new char[1024];
                int charLength = 0;
                // 将字符读入数组，若无输入字符流则堵塞,返回读入的字符个数，若吴可读的字符则返回1
                while ((charLength = inputStreamReader.read(textChar)) != -1) {
                    text.append(new String(textChar, 0, charLength));
                }
                inputStreamReader.close();
            } else {
                text.append("无数据");
            }
        } catch (Exception e) {
            text.append("无数据");
            e.printStackTrace();
        }
        return text.toString();
    }

}
