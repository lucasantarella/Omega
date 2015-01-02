package com.lucasantarella.omega;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewStory extends Activity {
	static final String TAG = ViewStory.class.getSimpleName().toString();
	Intent ViewStoryIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_story_layout);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		Log.d(TAG, "onCreated");
		Intent intent = getIntent();
		String title = intent.getStringExtra("STORY_TITLE");
		String content = intent.getStringExtra("STORY_CONTENT");
		String author = intent.getStringExtra("STORY_AUTHOR");
		String guid = intent.getStringExtra("STORY_GUID");
		TextView titleTextView = (TextView) findViewById(R.id.story_title);
		TextView contentTextView = (TextView) findViewById(R.id.story_content);
		TextView authorTextView = (TextView) findViewById(R.id.story_author);
		titleTextView.setText(title);
		contentTextView.setText(content);
		authorTextView.setText(author);
		Log.d(TAG, title);
		Log.d(TAG, content);
		Log.d(TAG, author);
		Uri webpage = Uri.parse(guid);
		ViewStoryIntent = new Intent(Intent.ACTION_VIEW, webpage);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.story, menu);
		if (ViewStoryIntent.resolveActivity(getPackageManager()) != null) {
			menu.findItem(R.id.action_guid).setIntent(ViewStoryIntent);
			menu.findItem(R.id.action_guid).setVisible(true);
		}else{
			menu.findItem(R.id.action_guid).setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
}
