package com.monet.bbc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;
import com.monet.bbc.adapter.LeaderboardAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardScreen extends AppCompatActivity {

    private CircleImageView img_leaderboardUser;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private LeaderboardAdapter leaderboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_screen);

        img_leaderboardUser = findViewById(R.id.img_leaderboardUser);
        recyclerView = findViewById(R.id.rv_leaderboard);

        linearLayoutManager = new LinearLayoutManager(this);
        leaderboardAdapter = new LeaderboardAdapter(this, 10);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(leaderboardAdapter);

        Glide.with(this).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(img_leaderboardUser);

    }
}
