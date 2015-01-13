package com.lucasantarella.omega;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Luca Santarella on 2015-1-2.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    public static final String TAG = DataAdapter.class.getSimpleName();
    List<AdapterItem> data = Collections.emptyList();
    private LayoutInflater inflater;

    public DataAdapter(Context context, List<AdapterItem> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rss_item_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AdapterItem item = data.get(position);
        Log.d(TAG, "onBindViewHolder");

        // Shorten title if neccessary
        if (item._title.length() > 37)
            holder.title.setText(item._title.substring(0, 34) + "...");
        else
            holder.title.setText(item._title);

        // Shorten description if neccessary
//        if (item._description.length() > 100)
//            holder.description.setText(item._description.substring(0, 97) + "...");
//        else
//            holder.description.setText(item._description);
        SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date d = null;
        try {
            d = f.parse(item._pubdate);
        } catch (ParseException e) {
            Log.e(TAG, "Failed to parse pubdate!");
        }
        holder.pubDate.setText(DateUtils.getRelativeTimeSpanString(d.getTime()));
//        holder.attachment.setImageDrawable(new LoadImage().execute(item._attchment));
        holder.id.setText(item._id);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LoadImage extends AsyncTask<String, Void, Drawable> {
        @Override
        protected Drawable doInBackground(String... params) {
            InputStream is = null;
            try {
                is = (InputStream) new URL(params[0]).getContent();
                return Drawable.createFromStream(is, "omegaAttachment");
            } catch (IOException e) {
                Log.e(TAG, "onCrashed while downloading image!\n", e);
                return null;
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView pubDate;
        //        TextView description;
        TextView id;
        ImageView attachment;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            pubDate = (TextView) itemView.findViewById(R.id.item_pub_date);
//            description = (TextView) itemView.findViewById(R.id.item_description);
            id = (TextView) itemView.findViewById(R.id.item_feed_id);
            attachment = (ImageView) itemView.findViewById(R.id.attachment_image_view);
        }
    }
}
