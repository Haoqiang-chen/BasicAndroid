package cc.aiknow.basicandroid.androidstore.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import okhttp3.internal.Version

/**
 * @Description: SQLiteOpenHelper Demo实现
 * @Author chenhaoqiang
 * @Since 2024/1/3
 * @Version 1.0
 */

class DemoSQLiteOpenHelper(context: Context,
                           name: String,
                           factory: SQLiteDatabase.CursorFactory?,
                           version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    companion object{
        const val DATABASE_VERSION = 1
    }

    /**
     * 数据库第一次创建的时候调用
     * 1. 用于创建数据库并进行一些初始化操作
     * 2. 仅当数据库未创建时调用第一次
     */
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")
        Log.e("DemoSQLiteOpenHelper", "onCreate")
    }

    /**
     * 数据库版本升级时调用
     * 1. 用于升级数据库
     * 2. 在处理数据库升级时，需要考虑目前已经存在的版本号和目标版本号
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.e("DemoSQLiteOpenHelper", "onUpgrade")
    }

    /**
     * 数据库版本升级时调用
     * 1. 如果需要降级时没有重写此方法，会执行父类方法从而抛出异常阻止降级
     */
    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 父类方法中默认拒绝降级，并且抛出异常
//        super.onDowngrade(db, oldVersion, newVersion)
        Log.e("DemoSQLiteOpenHelper", "onDowngrade")
    }

    /**
     * 数据库打开时调用
     * 1.数据库打开的时候调用，如果有升级或者降级操作，会在升级或者降级后调用
     */
    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        Log.e("DemoSQLiteOpenHelper", "onOpen")
    }
}