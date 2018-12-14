package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;
import com.monet.bbc.adapter.SimpleVideoAdapter;
import com.monet.bbc.fragment.HomeFragment;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.monet.bbc.utils.AppUtils.convertVideoTime;

public class SimpleVideoPlay extends AppCompatActivity implements View.OnClickListener {

    private VideoView video_SVP;
    private RelativeLayout rl_pauseLayout, rl_videoInfo, rl_autoPlayLayout;
    private ImageView img_svp_videoThumb, img_playVideo, img_fullScreen;
    private TextView tv_videoCurrentTime, tv_videoLength, tv_SVP_videoName, tv_bySomeone_SVP, tv_SVP_posted;
    private SeekBar sb_SVP;
    private CircleImageView img_SVP_user;
    private RecyclerView rv_svp;
    private SimpleVideoAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private Uri uri;
    private Handler handler;
    private Runnable runnable;
    private int hidePauseLayout = 0;
    private boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_video_play);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initView();
    }

    private void initView() {
        findViewById(R.id.img_playVideo).setOnClickListener(this);
        findViewById(R.id.img_SVP_back).setOnClickListener(this);
        findViewById(R.id.img_SVP_search).setOnClickListener(this);
        findViewById(R.id.img_fullScreen).setOnClickListener(this);
        findViewById(R.id.img_dots).setOnClickListener(this);
//        String videoURL = "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
        String videoURL = "https://www.radiantmediaplayer.com/media/bbb-360p.mp4";

        tv_videoLength = findViewById(R.id.tv_videoLength);
        tv_videoCurrentTime = findViewById(R.id.tv_videoCurrentTime);
        video_SVP = findViewById(R.id.video_SVP);
        rl_pauseLayout = findViewById(R.id.rl_pauseLayout);
        img_svp_videoThumb = findViewById(R.id.img_svp_videoThumb);
        tv_SVP_videoName = findViewById(R.id.tv_SVP_videoName);
        img_playVideo = findViewById(R.id.img_playVideo);
        img_SVP_user = findViewById(R.id.img_SVP_user);
        rv_svp = findViewById(R.id.rv_svp);
        sb_SVP = findViewById(R.id.sb_SVP);
        img_fullScreen = findViewById(R.id.img_fullScreen);
        rl_videoInfo = findViewById(R.id.rl_videoInfo);
        rl_autoPlayLayout = findViewById(R.id.rl_autoPlayLayout);
        video_SVP.setOnClickListener(this);
        rl_pauseLayout.setVisibility(View.GONE);

        img_fullScreen.setBackgroundResource(R.drawable.ic_baseline_fullscreen_enter);

        Bundle bundle = getIntent().getExtras();
        String image = bundle.getString("image");
        String name = bundle.getString("name");

        Glide.with(this).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(img_SVP_user);

        handler = new Handler();

        Glide.with(this).load(image).into(img_svp_videoThumb);
        tv_SVP_videoName.setText(name);
        setAdapter();
        setVideo(videoURL);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setVideo(String videoURLtoPlay) {
        rl_pauseLayout.setVisibility(View.VISIBLE);
        try {
            uri = Uri.parse(videoURLtoPlay);
            video_SVP.setVideoURI(uri);
        } catch (Exception e) {
            Toast.makeText(SimpleVideoPlay.this, "Something went wrong to play video", Toast.LENGTH_SHORT).show();
        }

        video_SVP.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPrepared(MediaPlayer mp) {
                rl_pauseLayout.setVisibility(View.GONE);
                img_svp_videoThumb.setVisibility(View.GONE);
                tv_videoLength.setText(convertVideoTime(video_SVP.getDuration()));
                video_SVP.start();
                sb_SVP.setMax(video_SVP.getDuration());
                setSeekBar();
            }
        });

        sb_SVP.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    video_SVP.seekTo(i);
                    if (!video_SVP.isPlaying()) {
                        video_SVP.start();
                        setSeekBar();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        video_SVP.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                rl_pauseLayout.setVisibility(View.VISIBLE);
                hidePauseLayout = 0;
                if (video_SVP.isPlaying()) {
                    img_playVideo.setBackgroundResource(R.drawable.ic_pause);
                } else {
                    img_playVideo.setBackgroundResource(R.drawable.ic_play);
                }
                return true;
            }
        });

    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new SimpleVideoAdapter(this, HomeFragment.imageList);
        rv_svp.setLayoutManager(layoutManager);
        rv_svp.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_playVideo:
                if (video_SVP.isPlaying()) {
                    video_SVP.pause();
                    handler.removeCallbacks(runnable);
                    img_playVideo.setBackgroundResource(R.drawable.ic_play);
                } else {
                    video_SVP.start();
                    setSeekBar();
                    img_playVideo.setBackgroundResource(R.drawable.ic_pause);
                    rl_pauseLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.img_SVP_back:
                finish();
                break;
            case R.id.img_SVP_search:
                Toast.makeText(this, "search function", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_fullScreen:

                if (!isFullScreen) {
                    img_fullScreen.setBackgroundResource(R.drawable.ic_baseline_fullscreen_exit);
                    isFullScreen = true;
                    rl_videoInfo.setVisibility(View.GONE);
                    rl_autoPlayLayout.setVisibility(View.GONE);
                    rv_svp.setVisibility(View.GONE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    img_fullScreen.setBackgroundResource(R.drawable.ic_baseline_fullscreen_enter);
                    isFullScreen = false;
                    rl_videoInfo.setVisibility(View.VISIBLE);
                    rl_autoPlayLayout.setVisibility(View.VISIBLE);
                    rv_svp.setVisibility(View.VISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                }

                break;
            case R.id.img_dots:
                Toast.makeText(this, "dots clik", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void setSeekBar() {
        sb_SVP.setProgress(video_SVP.getCurrentPosition());
        if (video_SVP.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    tv_videoCurrentTime.setText(convertVideoTime(video_SVP.getCurrentPosition()));
                    hidePauseLayout = hidePauseLayout + 1;
                    if (hidePauseLayout == 3) {
                        rl_pauseLayout.setVisibility(View.GONE);
                    }
                    setSeekBar();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        video_SVP.setVisibility(View.GONE);
    }
}
