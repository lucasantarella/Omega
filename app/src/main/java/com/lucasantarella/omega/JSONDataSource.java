package com.lucasantarella.omega;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.jsoup.Jsoup;

public class JSONDataSource {
    static final String TAG = FeedDataSource.class.getSimpleName();
    public String[] allColumns = {JSONHelper.COLUMN_ID, JSONHelper.COLUMN_TITLE,
            JSONHelper.COLUMN_AUTHOR, JSONHelper.COLUMN_PUBDATE, JSONHelper.COLUMN_CATEGORY, JSONHelper.COLUMN_DESCRIPTION,
            JSONHelper.COLUMN_CONTENT, JSONHelper.COLUMN_ATTACHMENT, JSONHelper.COLUMN_GUID};
    private SQLiteDatabase database;
    private JSONHelper jsonHelper;

    public JSONDataSource(Context context) {
        jsonHelper = new JSONHelper(context);
    }

    public void open() throws SQLException {
        database = jsonHelper.getWritableDatabase();
        Log.d(TAG, "onDatabaseOpen");
    }

    public void close() {
        database.close();
        Log.d(TAG, "onDatabaseClose");
    }

    public void insertIntoDatabase(String title, String author, String pubDate, String category,
                                   String description, String content, String attachment, String guid) {

        author = Jsoup.parse(author).text();
        title = Jsoup.parse(title).text();
        description = Jsoup.parse(description).text();
        content = Jsoup.parse(content).text();

        ContentValues values = new ContentValues();
        values.put(JSONHelper.COLUMN_TITLE, title);
        values.put(JSONHelper.COLUMN_AUTHOR, author);
        values.put(JSONHelper.COLUMN_PUBDATE, pubDate);
        values.put(JSONHelper.COLUMN_CATEGORY, category);
        values.put(JSONHelper.COLUMN_DESCRIPTION, description);
        values.put(JSONHelper.COLUMN_CONTENT, content);
        values.put(JSONHelper.COLUMN_ATTACHMENT, attachment);
        values.put(JSONHelper.COLUMN_GUID, guid);

        database.insert(JSONHelper.TABLE_FEED, null, values);
        Log.d(TAG, "onInsertIntoDatabase");
    }

    public void inValidateTable() {
        database.execSQL("delete from " + JSONHelper.TABLE_FEED);
        Log.d(TAG, "onTableInvalidated");
    }

    public Cursor getAllItems() {
        Cursor cursor = database.query(JSONHelper.TABLE_FEED, allColumns, null,
                null, null, null, null);
        Log.d(TAG, "onGetAllItems");
        return cursor;
    }

    public Cursor getRow(int id) {
        return database.query(JSONHelper.TABLE_FEED, allColumns, String.format("_id = '%s'", id), null, null, null, null, "1");
    }
}
