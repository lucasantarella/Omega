package com.lucasantarella.omega;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Luca on 1/12/2015.
 */
public class ViewStoryFragment extends Fragment {
    public static final String TAG = ViewStoryFragment.class.getSimpleName();
    JSONDataSource dataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.view_story_layout, container, false);
        dataSource = new JSONDataSource(getActivity());
        dataSource.open();
        Cursor c = dataSource.getRow(getArguments().getInt(MainActivity.ID_EXTRA));
        Log.d(TAG, String.format("Got %s number of rows", c.getCount()));
        Log.d(TAG, "GotExtra with ID: " + getArguments().getInt(MainActivity.ID_EXTRA));
        TextView titleTextView = (TextView) layout.findViewById(R.id.story_title);
        TextView contentTextView = (TextView) layout.findViewById(R.id.story_content);
        TextView authorTextView = (TextView) layout.findViewById(R.id.story_author);
        c.moveToFirst();
        titleTextView.setText(c.getString(1));
        contentTextView.setText(c.getString(6));
        authorTextView.setText(c.getString(2));

        Uri webpage = Uri.parse(c.getString(6));

        Intent ViewStoryIntent = new Intent(Intent.ACTION_VIEW, webpage);
        dataSource.close();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
