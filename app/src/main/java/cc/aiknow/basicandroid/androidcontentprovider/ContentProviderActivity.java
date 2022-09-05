package cc.aiknow.basicandroid.androidcontentprovider;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cc.aiknow.basicandroid.R;

public class ContentProviderActivity extends AppCompatActivity {

    private ContentResolver contentResolver;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        // 进程间的通信start
        contentResolver = getContentResolver();
        findViewById(R.id.read_data).setOnClickListener(v -> {
            Uri uri = Uri.parse("content://cc.aiknow.basicandroid.androidcontentprovider/user");
            Cursor cursor = contentResolver.query(uri, new String[]{"_id", "name"}, null, null, null);
            if (cursor == null) {
                return;
            }
            while (cursor.moveToNext()){
                Log.e("content resolver", "id:" + cursor.getInt(0) + "   name:" + cursor.getString(1));
            }
            cursor.close();
            // 进程间的通信end
            /**
             * 对于跨进成的通信，只需要一个进程提供ContentProvider并在清单文件中声明权限，读取数据的进程同时在清单文件中声明
             */
        });
    }
}