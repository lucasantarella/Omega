package com.lucasantarella.omega;

import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class GetRSSFeedData extends IntentService{
	
	public static final String TAG = GetRSSFeedData.class.getSimpleName().toString();
	public static final String NEW_FEED_ITEMS = "com.lucasantarella.omega.NEW_FEED_ITEMS";
	public static final String LOAD_TOGGLE = "com.lucasantarella.omega.LOAD_TOGGLE";

	RSSParser rssParser = new RSSParser();
	List<RSSItem> rssItems = new ArrayList<RSSItem>();
	RSSFeed rssFeed;
	
	
	public GetRSSFeedData() {
		super(TAG);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "onHandleIntent");
		sendBroadcast(new Intent(LOAD_TOGGLE));
		rssItems = rssParser.getRSSFeedItems("http://www.oratoryprepomega.org/category/news/op-news/feed/");
		FeedDataSource dataSource = new FeedDataSource(this);
		dataSource.open();
		dataSource.inValidateTable();
		for (RSSItem item : rssItems) {
			dataSource.insertIntoDatabase(item.get_title(), item.get_author(), item.get_pubdate(), item.get_description(), item.get_content(), item.get_guid());
		}	
		dataSource.close();
		sendBroadcast(new Intent(NEW_FEED_ITEMS));
		sendBroadcast(new Intent(LOAD_TOGGLE));
	}
}
