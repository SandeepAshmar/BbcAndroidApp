package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;

import com.monet.bbc.R;
import com.monet.bbc.adapter.RewardsAdapter;

import static com.monet.bbc.utils.AppUtils.checkConnection;

public class RewardsScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private RewardsAdapter adapter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_screen);

        toolbar = findViewById(R.id.tb_rewards);
        recyclerView = findViewById(R.id.rv_rewards);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new RewardsAdapter(this, 10);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        checkConnection(this);
        super.onResume();
    }
}
