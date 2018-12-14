package com.monet.bbc.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.monet.bbc.R;
import com.monet.bbc.activity.SimpleVideoPlay;

import java.util.ArrayList;


public class ImageSliderHome extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;

    public ImageSliderHome(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @SuppressLint("NewApi")
    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        final ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        Glide.with(context).load(images.get(position)).into(myImage);
        view.addView(myImageLayout, 0);

        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                Intent intent = new Intent(context, SimpleVideoPlay.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(myImage, "imageVideo");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);
                b.putString("image", images.get(position));
                intent.putExtras(b);
                context.startActivity(intent, options.toBundle());
            }
        });

        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
