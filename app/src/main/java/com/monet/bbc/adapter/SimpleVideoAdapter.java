package com.monet.bbc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monet.bbc.R;

public class SimpleVideoAdapter extends RecyclerView.Adapter<SimpleVideoAdapter.ViewHolder>{

    Context context;
    int size;
    String imageUrl;

    public SimpleVideoAdapter(Context context, int size, String url) {
        this.context = context;
        this.size = size;
        this.imageUrl = url;
    }

    @NonNull
    @Override
    public SimpleVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simple_video_play, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleVideoAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
