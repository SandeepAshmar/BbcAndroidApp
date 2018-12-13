package com.monet.bbc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;
import com.monet.bbc.adapter.SimpleVideoAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class SimpleVideoPlay extends AppCompatActivity implements View.OnClickListener{

    private VideoView video_SVP;
    private RelativeLayout rl_pauseLayout;
    private ImageView img_svp_videoThumb;
    private TextView tv_videoCurrentTime, tv_videoLength, tv_SVP_videoName, tv_bySomeone_SVP, tv_SVP_posted;
    private SeekBar sb_SVP;
    private CircleImageView img_SVP_user;
    private RecyclerView rv_svp;
    private SimpleVideoAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_video_play);

        findViewById(R.id.img_playVideo).setOnClickListener(this);
        findViewById(R.id.img_SVP_back).setOnClickListener(this);
        findViewById(R.id.img_SVP_search).setOnClickListener(this);
        findViewById(R.id.img_fullScreen).setOnClickListener(this);
        findViewById(R.id.img_dots).setOnClickListener(this);
        video_SVP = findViewById(R.id.video_SVP);
        rl_pauseLayout = findViewById(R.id.rl_pauseLayout);
        img_svp_videoThumb = findViewById(R.id.img_svp_videoThumb);
        tv_SVP_videoName = findViewById(R.id.tv_SVP_videoName);
        rv_svp = findViewById(R.id.rv_svp);
        video_SVP.setOnClickListener(this);
        rl_pauseLayout.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        String image = bundle.getString("image");
        String name = bundle.getString("name");

        Glide.with(this).load(image).into(img_svp_videoThumb);
        tv_SVP_videoName.setText(name);

        setAdapter();
    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new SimpleVideoAdapter(this, 15, "");
        rv_svp.setLayoutManager(layoutManager);
        rv_svp.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_playVideo:
                rl_pauseLayout.setVisibility(View.GONE);
                Toast.makeText(this, "Video will play again", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_SVP_back:
                finish();
                break;
            case R.id.img_SVP_search:
                Toast.makeText(this, "search function", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_fullScreen:
                Toast.makeText(this, "full screen", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_dots:
                Toast.makeText(this, "dots clik", Toast.LENGTH_SHORT).show();
                break;
            case R.id.video_SVP:
                rl_pauseLayout.setVisibility(View.VISIBLE);
                break;
        }
    }


}
