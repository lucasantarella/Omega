package com.lucasantarella.omega;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Windows on 22-12-2014.
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.MyViewHolder> {
    List<NavigationDrawerFragment.drawerListItem> data = Collections.emptyList();
    private LayoutInflater inflater;

    public NavigationAdapter(Context context, List<NavigationDrawerFragment.drawerListItem> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavigationDrawerFragment.drawerListItem current = data.get(position);
        holder.list.setText(current.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView list;

        public MyViewHolder(View itemView) {
            super(itemView);
            list = (TextView) itemView.findViewById(R.id.listItem);
        }
    }
}
