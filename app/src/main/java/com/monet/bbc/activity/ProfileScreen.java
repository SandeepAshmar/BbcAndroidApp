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
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.monet.bbc.R;
import com.monet.bbc.utils.AppPreference;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.monet.bbc.utils.AppPreference.getImageBase64;
import static com.monet.bbc.utils.AppPreference.getImageURL;
import static com.monet.bbc.utils.AppUtils.convertBase64ToBitmap;

@SuppressLint("NewApi")
public class ProfileScreen extends AppCompatActivity implements Animation.AnimationListener {

    private CircleImageView img_userProfile;
    private ImageView img_profileEdit;
    private TextView tv_userNameProfile, tv_userLocationProfile;
    private Toolbar toolbar;
    private Animation animZoomIn;
    private Animation animZoomOut;
    private boolean isProfileZoom = false;
    private float profileX , profileY, screenX, screenY;
    private View balckLayerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        img_userProfile = findViewById(R.id.img_userProfile);
        img_profileEdit = findViewById(R.id.img_profileEdit);
        tv_userNameProfile = findViewById(R.id.tv_userNameProfile);
        tv_userLocationProfile = findViewById(R.id.tv_userLocationProfile);
        balckLayerProfile = findViewById(R.id.balckLayerProfile);
        toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        profileX = img_userProfile.getX();
        profileY = img_userProfile.getY();

        screenX= this.getResources().getDisplayMetrics().xdpi / 4;
        screenY= this.getResources().getDisplayMetrics().ydpi / 4;

        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.profile_zoom_in);
        animZoomIn.setAnimationListener(this);

        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.profile_zoom_out);
        animZoomOut.setAnimationListener(this);

        img_profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chnageActivity();
            }
        });

        img_userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileImageLocation();
            }
        });

        balckLayerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileImageLocation();
            }
        });

    }

    private void setProfileImageLocation() {
        if (isProfileZoom) {
            zoomOut(img_userProfile);
            img_userProfile.startAnimation(animZoomOut);
            isProfileZoom = false;
            balckLayerProfile.setVisibility(View.GONE);
        } else {
            zoomIn(img_userProfile);
            img_userProfile.startAnimation(animZoomIn);
            isProfileZoom = true;
            balckLayerProfile.setVisibility(View.VISIBLE);
        }
    }

    private void zoomIn(CircleImageView target) {
        target.animate()
                .x(screenX)
                .y(screenY)
                .setDuration(300)
                .start();
    }

    private void zoomOut(CircleImageView viewToMove) {
        viewToMove.animate()
                .x(profileX)
                .y(profileY)
                .setDuration(300)
                .start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void chnageActivity() {
        Intent intent = new Intent(this, EditProfileScreen.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(img_userProfile, "editProfileUserImage");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) this, pairs);
        startActivity(intent, options.toBundle());
    }

    @Override
    protected void onResume() {
        if (getImageBase64(this).isEmpty()) {
            Glide.with(this).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg")
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .into(img_userProfile);
        } else {
            img_userProfile.setImageBitmap(convertBase64ToBitmap(getImageBase64(this)));
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (isProfileZoom) {
            zoomOut(img_userProfile);
            img_userProfile.startAnimation(animZoomOut);
            isProfileZoom = false;
            balckLayerProfile.setVisibility(View.GONE);
        } else {
            finish();
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}