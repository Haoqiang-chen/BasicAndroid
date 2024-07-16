package cc.aiknow.basicandroid.androidstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import cc.aiknow.basicandroid.MyApplication;
import cc.aiknow.basicandroid.R;
import cc.aiknow.basicandroid.androidstore.database.DemoSQLiteOpenHelper;

public class StoreActivity extends AppCompatActivity {

    private EditText sourceText;
    private Button storeButton, getDataButton, initDb, insertDbData;
    private TextView showDataText;
    private DemoSQLiteOpenHelper demoSQLiteOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        initView();
        initListener();
        initDataBase();
    }

    private void initDataBase() {
        demoSQLiteOpenHelper = new DemoSQLiteOpenHelper(this, "DemoSQLiteOpenHelper.db", null, DemoSQLiteOpenHelper.DATABASE_VERSION);
    }

    private void initView() {
        sourceText = findViewById(R.id.sourceText);
        storeButton = findViewById(R.id.storeButton);
        getDataButton = findViewById(R.id.getDataButton);
        showDataText = findViewById(R.id.showDataText);
        initDb = findViewById(R.id.initDb);
        insertDbData = findViewById(R.id.insertDbData);
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

        initDb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SQLiteDatabase writableDatabase = demoSQLiteOpenHelper.getReadableDatabase();

                File file = MyApplication.context.getFilesDir();
                File database = new File(file.getPath() + File.separator + "DemoSQLiteOpenHelper.db");

                SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(database, null);
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");

                SQLiteDatabase sqLiteDatabase1 =  StoreActivity.this.openOrCreateDatabase("ContextOpenOrCreateDatabase.db", Context.MODE_PRIVATE, null);
                sqLiteDatabase1.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");

            }
        } );

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
