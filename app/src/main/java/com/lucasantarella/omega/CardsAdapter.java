package com.lucasantarella.omega;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Luca on 1/2/2015.
 */
public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder>{
    public static final String TAG = CardsAdapter.class.getSimpleName().toString();
    private final LayoutInflater inflater;
    List<RSSItem> data = Collections.emptyList();

    public CardsAdapter(Context context, List<RSSItem> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        Log.d(TAG, "onConstructedAdapter");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = inflater.inflate(R.layout.rss_item_list_row, parent, false);
        ViewHolder cardsHolder = new ViewHolder(view);
        return cardsHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        RSSItem item = data.get(position);
        Log.d(TAG, "onBindViewHolder");
        viewHolder.title.setText(item._title);
        viewHolder.content.setText(item._content);
        viewHolder.author.setText(item._author);
        viewHolder.guid.setText(item._guid);
        viewHolder.pubDate.setText(item._pubdate);
        viewHolder.description.setText(item._description);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView title;
        TextView author;
        TextView guid;
        TextView pubDate;
        TextView conTitle;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_full_title);
            content = (TextView) itemView.findViewById(R.id.item_content);
            author = (TextView) itemView.findViewById(R.id.item_author);
            guid = (TextView) itemView.findViewById(R.id.item_guid);
            pubDate = (TextView) itemView.findViewById(R.id.item_pub_date);
            conTitle = (TextView) itemView.findViewById(R.id.item_title);
            description = (TextView) itemView.findViewById(R.id.item_description);
        }
    }
}
