package com.lucasantarella.omega;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class JSONHelper extends SQLiteOpenHelper {

    public static final String TABLE_FEED = "json";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "_title";
    public static final String COLUMN_AUTHOR = "_author";
    public static final String COLUMN_PUBDATE = "_date";
    public static final String COLUMN_CATEGORY = "_category";
    public static final String COLUMN_DESCRIPTION = "_description";
    public static final String COLUMN_CONTENT = "_content";
    public static final String COLUMN_ATTACHMENT = "_attachment";
    public static final String COLUMN_GUID = "_url";
    private static final String TAG = DBHelper.class.getSimpleName();
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "json.db";

    public JSONHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_FEED + " (" +
                        COLUMN_ID + " integer primary key autoincrement, " +
                        COLUMN_TITLE + " text not null, " +
                        COLUMN_AUTHOR + " text not null, " +
                        COLUMN_PUBDATE + " text not null, " +
                        COLUMN_CATEGORY + " text not null, " +
                        COLUMN_DESCRIPTION + " text not null, " +
                        COLUMN_CONTENT + " text not null, " +
                        COLUMN_ATTACHMENT + " text not null, " +
                        COLUMN_GUID + " text not null);"
        );
        Log.d(TAG, "onCreated database with SQl");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);
        onCreate(db);
        Log.d(TAG, "onUpgrade SQLite Database " + db.toString() + " from oldversion " + oldVersion + " to newVersion " + newVersion);
    }

}
