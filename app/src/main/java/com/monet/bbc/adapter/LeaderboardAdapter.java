package com.monet.bbc.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monet.bbc.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private Context context;
    private int size;

    public LeaderboardAdapter(Context context, int size) {
        this.context = context;
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.rank.setText(""+(position+1)+".");

        holder.madelTop.setBackgroundResource(R.drawable.ic_defaultmedaltop);
        holder.madelBottom.setBackgroundResource(R.drawable.ic_defaultmedalbottom);

        if(position == 0){
            holder.madelTop.setBackgroundResource(R.drawable.ic_goldmedaltop);
            holder.madelBottom.setBackgroundResource(R.drawable.ic_goldmedalbottom);
        }

        if(position == 1){
            holder.madelTop.setBackgroundResource(R.drawable.ic_silvermedaltop);
            holder.madelBottom.setBackgroundResource(R.drawable.ic_silvermedalbottom);
        }
        if(position == 2){
            holder.madelTop.setBackgroundResource(R.drawable.ic_bronzemedaltop);
            holder.madelBottom.setBackgroundResource(R.drawable.ic_bronzemedalbottom);
        }

    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rank, name, country, points;
        ImageView madelTop, madelBottom;
        CircleImageView userImage;

        public ViewHolder(View itemView) {
            super(itemView);

            rank = itemView.findViewById(R.id.tv_userRankLeaderboardItem);
            name = itemView.findViewById(R.id.tv_leaderboardNameItem);
            country = itemView.findViewById(R.id.tv_leaderboardCountryItem);
            points = itemView.findViewById(R.id.tv_leaderboardPoints);
            madelTop = itemView.findViewById(R.id.img_leaderboardMadelTop);
            madelBottom = itemView.findViewById(R.id.img_leaderboardMadelBottom);
            userImage = itemView.findViewById(R.id.img_leaderboardUserItem);
        }
    }
}
