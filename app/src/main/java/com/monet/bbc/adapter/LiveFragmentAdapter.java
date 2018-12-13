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

public class LiveFragmentAdapter extends RecyclerView.Adapter<LiveFragmentAdapter.ViewHolder> {
    Context mContext;
    int size;

    public LiveFragmentAdapter(Context mContext, int size) {
        this.mContext = mContext;
        this.size = size;
    }

    @NonNull
    @Override
    public LiveFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_fragment_recent, parent,false);


        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveFragmentAdapter.ViewHolder holder, int position) {
        Glide.with(mContext).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(holder.imageLiveFragment);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageLiveFragment;
        public ViewHolder(View itemView) {
            super(itemView);
            imageLiveFragment = itemView.findViewById(R.id.img_recent_live_item);
        }
    }
}
