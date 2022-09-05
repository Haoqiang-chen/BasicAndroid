package cc.aiknow.basicandroid.androidcontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @Description: 自定义的SQLite数据库创建以及版本控制辅助类
 * @Author chenhaoqiang
 * @Since 2022/4/7
 * @Version 1.0
 */
public class SelfDateBaseHelper extends SQLiteOpenHelper {
    /**
     * 数据库名称
     */
    private static final String DATA_BASE_NAME = "db_self";

    /**
     * 数据库版本
     */
    private static final int DATA_BASE_VERSION = 1;

    /**
     * 用户表的表名
     */
    public static final String USER_TABLE_NAME = "user";

    /**
     * 类型表的表名
     */
    public static final String TYPE_TABLE_NAME = "type";

    public SelfDateBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TYPE_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
