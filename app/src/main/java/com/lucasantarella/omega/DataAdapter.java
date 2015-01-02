package com.lucasantarella.omega;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Luca Santarella on 2015-1-2.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    public static final String TAG = DataAdapter.class.getSimpleName().toString();
    List<RSSItem> data= Collections.emptyList();
    private LayoutInflater inflater;
    public DataAdapter(Context context, List<RSSItem> data){
        inflater=LayoutInflater.from(context);
        this.data=data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.rss_item_list_row, parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RSSItem item = data.get(position);
        Log.d(TAG, "onBindViewHolder");
        holder.title.setText(item._title);
        if (item._title.length() > 100)
            holder.conTitle.setText(item._title.substring(0, 97) + "...");
        else
            holder.conTitle.setText(item._title);
        holder.content.setText(item._content);
        holder.author.setText(item._author);
        holder.guid.setText(item._guid);
        holder.pubDate.setText(item._pubdate);
        holder.description.setText(item._description);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //    @Override
//    public int getViewTypeCount() {
//        return 1;
//    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView title;
        TextView author;
        TextView guid;
        TextView pubDate;
        TextView conTitle;
        TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_full_title);
            conTitle = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.item_content);
            author = (TextView) itemView.findViewById(R.id.item_author);
            guid = (TextView) itemView.findViewById(R.id.item_guid);
            pubDate = (TextView) itemView.findViewById(R.id.item_pub_date);
            conTitle = (TextView) itemView.findViewById(R.id.item_title);
            description = (TextView) itemView.findViewById(R.id.item_description);
        }
    }
}
