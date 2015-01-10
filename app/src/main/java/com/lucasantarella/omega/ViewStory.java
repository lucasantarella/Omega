package com.lucasantarella.omega;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewStory extends ActionBarActivity {
    static final String TAG = ViewStory.class.getSimpleName();
    Intent ViewStoryIntent;
    JSONDataSource dataSource;
//    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.view_story_layout);
        Log.d(TAG, "onCreated");
        Intent intent = getIntent();

        String id = intent.getStringExtra(MainFragment.getIntentExtraId());

        dataSource = new JSONDataSource(this);
        dataSource.open();
        Cursor c = dataSource.getRow(Integer.parseInt(id));

        TextView titleTextView = (TextView) findViewById(R.id.story_title);
        TextView contentTextView = (TextView) findViewById(R.id.story_content);
        TextView authorTextView = (TextView) findViewById(R.id.story_author);
        c.moveToFirst();
        titleTextView.setText(c.getString(1));
        contentTextView.setText(c.getString(6));
        authorTextView.setText(c.getString(2));

        Uri webpage = Uri.parse(c.getString(6));

        ViewStoryIntent = new Intent(Intent.ACTION_VIEW, webpage);
        dataSource.close();
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

}
