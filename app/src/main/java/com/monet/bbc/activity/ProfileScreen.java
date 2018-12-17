package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("NewApi")
public class ProfileScreen extends AppCompatActivity {

    private String bannerImage = "https://png.pngtree.com/thumb_back/fw800/back_pic/00/02/73/21561a5ee52c746.jpg";
    private ImageView img_profileBanner, img_profileEdit, blur;
    private CircleImageView img_userImageProfile;
    private TextView tv_profileUserName, tv_profileLocation;
    private ScrollView profileScroll;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        img_profileBanner = findViewById(R.id.img_profileBanner);
        img_profileEdit = findViewById(R.id.img_profileEdit);
        img_userImageProfile = findViewById(R.id.img_userImageProfile);
        tv_profileUserName = findViewById(R.id.tv_profileUserName);
        tv_profileLocation = findViewById(R.id.tv_profileLocation);
        profileScroll = findViewById(R.id.profileScroll);
        blur = findViewById(R.id.blur);

        blur.setVisibility(View.GONE);

        Glide.with(this).load(bannerImage).into(img_profileBanner);
        Glide.with(this).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(img_userImageProfile);

        img_profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chnageActivity();
            }
        });

        profileScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = profileScroll.getScrollY();
                Log.d("scroll", "scrollY: " + scrollX);

                if(scrollX == 0){
                    blur.setVisibility(View.GONE);
                }else{
                    blur.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void chnageActivity() {
        Intent intent = new Intent(this, EditProfileScreen.class);
        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(img_userImageProfile, "editProfileUserImage");
        pairs[1] = new Pair<View, String>(tv_profileUserName, "editProfileUserName");
        pairs[2] = new Pair<View, String>(tv_profileLocation, "editProfileUserLocation");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) this, pairs);
        startActivity(intent, options.toBundle());
    }
}
