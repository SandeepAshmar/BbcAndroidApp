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

public class LiveFragmentAdapter extends RecyclerView.Adapter<LiveFragmentAdapter.ViewHolder> {
    Context mContext;
    ArrayList<String> imageList;

    public LiveFragmentAdapter(Context mContext, ArrayList<String> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public LiveFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_fragment_recent, parent, false);


        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull final LiveFragmentAdapter.ViewHolder holder, final int position) {
        Glide.with(mContext).load(imageList.get(position)).into(holder.imageLiveFragment);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                Intent intent = new Intent(mContext, SimpleVideoPlay.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(holder.imageLiveFragment, "imageVideo");
                pairs[1] = new Pair<View, String>(holder.tv_recent_live_item_title, "videoName");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, pairs);
                b.putString("image", imageList.get(position));
                b.putString("name", holder.tv_recent_live_item_title.getText().toString());
                intent.putExtras(b);
                mContext.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageLiveFragment;
        TextView tv_recent_live_item_title;

        public ViewHolder(View itemView) {
            super(itemView);
            imageLiveFragment = itemView.findViewById(R.id.img_recent_live_item);
            tv_recent_live_item_title = itemView.findViewById(R.id.tv_recent_live_item_title);
        }
    }
}
