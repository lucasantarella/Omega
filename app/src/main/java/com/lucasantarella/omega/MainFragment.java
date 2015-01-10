package com.lucasantarella.omega;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();
    public static final String INTENT_EXTRA_ID = "com.lucasantarella.omega.ViewStory.ID";
    static JSONDataSource dataSource;
    private RecyclerView recyclerView;
    private IntentFilter newItems;
    private UpdateReceiver reciever;

    public MainFragment() {
        // Required empty public constructor
    }

    public static String getIntentExtraId() {
        return INTENT_EXTRA_ID;
    }

    public static List<AdapterItem> getData() {
        //load only static data inside a drawer
        List<AdapterItem> dataSet = new ArrayList<>();
        Log.d(TAG, "onGetData");
        dataSource.open();
        Cursor c = dataSource.getAllItems();

        Log.d(TAG, String.format("Cursor count: %s", c.getCount()));
        c.moveToFirst();
        for (int i = 1; i <= c.getCount(); i++) {
            AdapterItem item = new AdapterItem();

            item.set_id(c.getString(0));
            item.set_title(c.getString(1));
            item.set_author(c.getString(2));
            item.set_pubdate(c.getString(3));
            item.set_category(c.getString(4));
            item.set_description(c.getString(5));
            item.set_content(c.getString(6));
            item.set_attchment(c.getString(7));
            item.set_guid(c.getString(8));
            dataSet.add(item);

            c.moveToNext();
        }
        dataSource.close();
        Log.d(TAG, "onGetData finished");
        return dataSet;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new JSONDataSource(getActivity());
        Log.d(TAG, "onCreate");
        newItems = new IntentFilter(GetRSSFeedData.NEW_FEED_ITEMS);
        reciever = new UpdateReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(reciever, newItems);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(reciever);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.adapterList);
        DataAdapter adapter = new DataAdapter(getActivity(), getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClick(getActivity(), recyclerView, new RecyclerItemClick.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView id = (TextView) view.findViewById(R.id.item_feed_id);
                Log.d(TAG, "onClicked with ID: " + id.getText().toString());
                Intent viewStory = new Intent(getActivity(), ViewStory.class);
                viewStory.putExtra(INTENT_EXTRA_ID, id.getText().toString());
                startActivity(viewStory);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        return layout;
    }

    public void refreshListView() {
        recyclerView.swapAdapter(new DataAdapter(getActivity(), getData()), true);
        Log.d(TAG, "onCursorSwapped");
    }

    class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            MainFragment.this.refreshListView();
            Log.d(UpdateReceiver.class.getSimpleName(), "onReceived");
        }
    }
}
