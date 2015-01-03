package com.lucasantarella.omega;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.jsoup.Jsoup;

public class FeedDataSource {
    static final String TAG = FeedDataSource.class.getSimpleName();
    public String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_TITLE,
            DBHelper.COLUMN_AUTHOR, DBHelper.COLUMN_PUBDATE, DBHelper.COLUMN_DESCRIPTION,
            DBHelper.COLUMN_CONTENT, DBHelper.COLUMN_GUID};
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public FeedDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        Log.d(TAG, "onDatabaseOpen");
    }

    public void close() {
        database.close();
        Log.d(TAG, "onDatabaseClose");
    }

    public void insertIntoDatabase(String title, String author, String pubDate,
                                   String description, String content, String guid) {

        author = Jsoup.parse(author).text();
        title = Jsoup.parse(title).text();
        description = Jsoup.parse(description).text();
        content = Jsoup.parse(content).text();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TITLE, title);
        values.put(DBHelper.COLUMN_AUTHOR, author);
        values.put(DBHelper.COLUMN_PUBDATE, pubDate);
        values.put(DBHelper.COLUMN_DESCRIPTION, description);
        values.put(DBHelper.COLUMN_CONTENT, content);
        values.put(DBHelper.COLUMN_GUID, guid);

        database.insert(DBHelper.TABLE_FEED, null, values);
        Log.d(TAG, "onInsertIntoDatabase");
    }

    public void inValidateTable() {
        database.execSQL("delete from " + DBHelper.TABLE_FEED);
        Log.d(TAG, "onTableInvalidated");
    }

    public Cursor getAllItems() {
        Cursor cursor = database.query(DBHelper.TABLE_FEED, allColumns, null,
                null, null, null, null);
        Log.d(TAG, "onGetAllItems");
        return cursor;
    }

    public Cursor getRow(int id) {
        return database.query(DBHelper.TABLE_FEED, allColumns, String.format("_id = '%s'", id), null, null, null, null, "1");
    }
}
