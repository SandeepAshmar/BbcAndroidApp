package com.monet.bbc.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;
import com.monet.bbc.activity.SimpleVideoPlay;

import java.util.ArrayList;

public class SimpleVideoAdapter extends RecyclerView.Adapter<SimpleVideoAdapter.ViewHolder> {

    Context context;
    ArrayList<String> imageList;

    public SimpleVideoAdapter(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public SimpleVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simple_video_play, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Glide.with(context).load(imageList.get(position)).into(holder.img_related_item);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_related_item;

        public ViewHolder(View itemView) {
            super(itemView);

            img_related_item = itemView.findViewById(R.id.img_related_item);
        }
    }
}
