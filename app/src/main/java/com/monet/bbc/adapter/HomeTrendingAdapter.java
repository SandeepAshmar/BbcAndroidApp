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

    @Override
    public void onBindViewHolder(@NonNull HomeTrendingAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(imageList.get(position)).into(holder.img_homeTrending_item);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_homeTrending_item;

        public ViewHolder(View itemView) {
            super(itemView);

            img_homeTrending_item = itemView.findViewById(R.id.img_homeTrending_item);
        }
    }
}
