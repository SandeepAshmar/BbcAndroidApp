package com.monet.bbc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveVideoPlay extends AppCompatActivity {

    CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getLayoutInflater().inflate(R.layout.activity_live_video_play, null);
        v.setKeepScreenOn(true);
        setContentView(v);

        userImage = findViewById(R.id.img_liveQuestionUser);

        Glide.with(this).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(userImage);

    }
}
