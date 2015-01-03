package com.lucasantarella.omega;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_FEED = "feed";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "_title";
    public static final String COLUMN_AUTHOR = "_author";
    public static final String COLUMN_PUBDATE = "_pubdate";
    public static final String COLUMN_DESCRIPTION = "_description";
    public static final String COLUMN_CONTENT = "_content";
    public static final String COLUMN_GUID = "_guid";
    private static final String CREATE_DB_SQL = "create table " + TABLE_FEED + " (" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_TITLE + " text not null, " + COLUMN_AUTHOR + " text not null, " + COLUMN_PUBDATE + " text not null, " + COLUMN_DESCRIPTION + " text not null, " + COLUMN_CONTENT + " text not null, " + COLUMN_GUID + " text not null);";
    private static final String TAG = DBHelper.class.getSimpleName();
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "feed.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_SQL);
        Log.d(TAG, "onCreated database with SQL: " + CREATE_DB_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);
        onCreate(db);
        Log.d(TAG, "onUpgrade SQLite Database " + db.toString() + " from oldversion " + oldVersion + " to newVersion " + newVersion);
    }

}
