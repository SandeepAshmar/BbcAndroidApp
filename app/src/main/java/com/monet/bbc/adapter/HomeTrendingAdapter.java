package com.monet.bbc.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;
import com.monet.bbc.activity.SimpleVideoPlay;

import java.util.ArrayList;

public class HomeTrendingAdapter extends RecyclerView.Adapter<HomeTrendingAdapter.ViewHolder> {

    Context context;
    ArrayList<String> imageList;

    public HomeTrendingAdapter(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public HomeTrendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_trending, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull final HomeTrendingAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(imageList.get(position)).into(holder.img_homeTrending_item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                Intent intent = new Intent(context, SimpleVideoPlay.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(holder.img_homeTrending_item, "imageVideo");
                pairs[1] = new Pair<View, String>(holder.tv_homeTrending_item_title, "videoName");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);
                b.putString("image", imageList.get(position));
                b.putString("name", holder.tv_homeTrending_item_title.getText().toString());
                intent.putExtras(b);
                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_homeTrending_item;
        TextView tv_homeTrending_item_title;

        public ViewHolder(View itemView) {
            super(itemView);

            img_homeTrending_item = itemView.findViewById(R.id.img_homeTrending_item);
            tv_homeTrending_item_title = itemView.findViewById(R.id.tv_homeTrending_item_title);
        }
    }
}
