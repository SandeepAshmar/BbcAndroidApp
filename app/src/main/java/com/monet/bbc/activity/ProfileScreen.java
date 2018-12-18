package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("NewApi")
public class ProfileScreen extends AppCompatActivity {

    private CircleImageView img_userProfile;
    private ImageView img_profileEdit;
    private TextView tv_userNameProfile, tv_userLocationProfile;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        img_userProfile = findViewById(R.id.img_userProfile);
        img_profileEdit = findViewById(R.id.img_profileEdit);
        tv_userNameProfile = findViewById(R.id.tv_userNameProfile);
        tv_userLocationProfile = findViewById(R.id.tv_userLocationProfile);
        toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Glide.with(this).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(img_userProfile);

        img_profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chnageActivity();
            }
        });

    }

    private void chnageActivity() {
        Intent intent = new Intent(this, EditProfileScreen.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(img_userProfile, "editProfileUserImage");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) this, pairs);
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}
