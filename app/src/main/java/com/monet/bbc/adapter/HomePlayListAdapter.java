package com.monet.bbc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;

import java.util.ArrayList;

public class HomePlayListAdapter extends RecyclerView.Adapter<HomePlayListAdapter.ViewHolder> {

    Context context;
    ArrayList<String> imageList;

    public HomePlayListAdapter(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_playlist, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(imageList.get(position)).into(holder.img_homePlayList_item);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_homePlayList_item;
        public ViewHolder(View itemView) {
            super(itemView);

            img_homePlayList_item = itemView.findViewById(R.id.img_homePlayList_item);
        }
    }
}
