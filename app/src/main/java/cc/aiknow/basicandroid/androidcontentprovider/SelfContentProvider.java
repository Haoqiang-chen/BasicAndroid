package cc.aiknow.basicandroid.androidcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description: 自定义的内容提供器
 * @Author chenhaoqiang
 * @Since 2022/4/7
 * @Version 1.0
 */
public class SelfContentProvider extends ContentProvider {

    public static final int NOT_MATCH = 0;
    public static final int MATCH_USER = 1;
    public static final int MATCH_TYPE = 2;

    /**
     * 数据库
     */
    private SQLiteDatabase database;

    private static UriMatcher uriMatcher = new UriMatcher(NOT_MATCH);

    private static final String AUTHORITY = "cc.aiknow.basicandroid.androidcontentprovider";

    static {
        uriMatcher.addURI(AUTHORITY, SelfDateBaseHelper.USER_TABLE_NAME, MATCH_USER);
        uriMatcher.addURI(AUTHORITY, SelfDateBaseHelper.TYPE_TABLE_NAME, MATCH_TYPE);
    }

    @Override
    public boolean onCreate() {
        database = new SelfDateBaseHelper(getContext()).getWritableDatabase();
        database.execSQL("delete from user");
        database.execSQL("insert into user values(1, 'alex')");
        database.execSQL("insert into user values(2, 'chris')");

        database.execSQL("delete from type");
        database.execSQL("insert into type values(1, 'android')");
        database.execSQL("insert into type values(2, 'ios')");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match = getMatch(uri);
        if (match == MATCH_USER) {
            return database.query(SelfDateBaseHelper.USER_TABLE_NAME, projection, selection, selectionArgs, null, null,  sortOrder, null);
        } else if (match == MATCH_TYPE) {
            return database.query(SelfDateBaseHelper.TYPE_TABLE_NAME, projection, selection, selectionArgs, null, null,  sortOrder, null);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private int getMatch(Uri uri) {
       return uriMatcher.match(uri);
    }
}
