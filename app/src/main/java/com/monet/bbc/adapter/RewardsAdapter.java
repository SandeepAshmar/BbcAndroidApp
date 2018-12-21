package com.monet.bbc.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.ScratchImageView;
import com.monet.bbc.R;

import java.util.ArrayList;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.ViewHolder> {

    Context context;
    int size;
    ScratchImageView simg_scratch;
    int pos = -1;

    public RewardsAdapter(Context context, int size) {
        this.context = context;
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rewards, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.rl_scratch.setVisibility(View.VISIBLE);
        holder.ll_couponLayout.setVisibility(View.GONE);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
        animation.setDuration(500);
        holder.itemView.startAnimation(animation);

        if (pos == position) {
            holder.rl_scratch.setVisibility(View.GONE);
            holder.ll_couponLayout.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_scratch;
        private TextView tv_rewardItemPoint, tv_rewardItemDiscountOn, tv_rewardItemDate;
        private LinearLayout ll_couponLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            rl_scratch = itemView.findViewById(R.id.rl_scratch);
            ll_couponLayout = itemView.findViewById(R.id.ll_couponLayout);
        }
    }

    private void openDialog(ViewHolder holder, final int position) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reward_dialog);

        simg_scratch = dialog.findViewById(R.id.simg_scratch);

        if(holder.rl_scratch.getVisibility() == View.VISIBLE){
            simg_scratch.setVisibility(View.VISIBLE);
        }else{
            simg_scratch.setVisibility(View.GONE);
        }

        simg_scratch.setRevealListener(new ScratchImageView.IRevealListener() {
            @Override
            public void onRevealed(ScratchImageView scratchImageView) {
            }

            @Override
            public void onRevealPercentChangedListener(ScratchImageView scratchImageView, float v) {
                if (v > 0.03 && v < 0.04) {
                    Log.d("done", "done");
                    pos = position;
                    notifyItemChanged(position);
                }
            }
        });

        dialog.show();
    }


}
