package com.monet.bbc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {

    Context context;
    int size;
    boolean isGridView;
    boolean isAddedFavorite = false;

    public PlayListAdapter(Context context, int size, boolean isGridView) {
        this.context = context;
        this.size = size;
        this.isGridView = isGridView;
    }

    @NonNull
    @Override
    public PlayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (isGridView) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_playlist_grid, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_playlist, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(holder.imageView);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
        animation.setDuration(500);
        holder.itemView.startAnimation(animation);

        holder.img_playlist_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Share" + position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.img_playlist_add_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "PlayList" + position, Toast.LENGTH_SHORT).show();
                notifyItemRemoved(position);
                size = size - 1;
            }
        });

        holder.img_playlist_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Love" + position, Toast.LENGTH_SHORT).show();
                if (isAddedFavorite) {
                    holder.img_playlist_add_remove.setBackgroundResource(R.drawable.ic_playlist_added);
                    isAddedFavorite = false;
                } else {
                    holder.img_playlist_add_remove.setBackgroundResource(R.drawable.ic_playlistadd);
                    isAddedFavorite = true;
                }
                notifyItemChanged(position);


            }
        });

    }

    @Override
    public int getItemCount() {
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, img_playlist_share, img_playlist_add_remove, img_playlist_love;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_playlist_item);
            img_playlist_share = itemView.findViewById(R.id.img_playlist_share);
            img_playlist_add_remove = itemView.findViewById(R.id.img_playlist_add_remove);
            img_playlist_love = itemView.findViewById(R.id.img_playlist_love);
        }
    }

}
