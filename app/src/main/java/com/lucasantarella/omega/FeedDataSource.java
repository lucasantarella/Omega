package com.lucasantarella.omega;

import org.jsoup.Jsoup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FeedDataSource {
	static final String TAG = FeedDataSource.class.getSimpleName().toString();
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	public String[] allColumns = { dbHelper.COLUMN_ID, dbHelper.COLUMN_TITLE,
			dbHelper.COLUMN_TITLE_CONDENSED, dbHelper.COLUMN_AUTHOR,
			dbHelper.COLUMN_PUBDATE, dbHelper.COLUMN_DESCRIPTION,
			dbHelper.COLUMN_CONTENT, dbHelper.COLUMN_GUID };

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
		String titleCondensed;
		title = Jsoup.parse(title).text();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_TITLE, title);
		if (title.length() > 100)
			titleCondensed = title.substring(0, 97) + "...";
		else
			titleCondensed = title;
		values.put(DBHelper.COLUMN_TITLE_CONDENSED, titleCondensed);
		author = Jsoup.parse(author).text();
		values.put(DBHelper.COLUMN_AUTHOR, author);
		values.put(DBHelper.COLUMN_PUBDATE, pubDate);
		description = Jsoup.parse(description).text();
		if (description.length() > 100)
			description = description.substring(0, 97) + "...";
		values.put(DBHelper.COLUMN_DESCRIPTION, description);
		content = Jsoup.parse(content).text();
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
}
