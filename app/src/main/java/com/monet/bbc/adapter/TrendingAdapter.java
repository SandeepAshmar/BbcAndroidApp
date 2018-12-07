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

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {

    Context context;
    boolean isGridView;
    int size;

    public TrendingAdapter(Context mContext, int size, boolean isGridView) {
        this.context = mContext;
        this.isGridView = isGridView;
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (isGridView) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_trending, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_trending, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return size;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_trending_item);
        }
    }
}
