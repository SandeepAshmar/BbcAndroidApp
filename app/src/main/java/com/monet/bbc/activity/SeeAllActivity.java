package com.monet.bbc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monet.bbc.R;
import com.monet.bbc.adapter.HomeShowsAdapter;
import com.monet.bbc.adapter.SeeAllAdapter;
import com.monet.bbc.adapter.TrendingAdapter;
import com.monet.bbc.fragment.HomeFragment;

import static com.monet.bbc.utils.AppUtils.checkConnection;

public class SeeAllActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbar_title, tv_seeAllLable;
    private RecyclerView recyclerView;
    private SeeAllAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private ImageView changeViewTrending;
    private boolean isGridView = true;
    private String trending, shows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        toolbar = findViewById(R.id.toolbar_trending);
        toolbar_title = findViewById(R.id.toolbar_title);
        tv_seeAllLable = findViewById(R.id.tv_seeAllLable);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        changeViewTrending = findViewById(R.id.img_change_view_trendingSeeAll);
        recyclerView = findViewById(R.id.rv_seeAll);

        Bundle bundle = getIntent().getExtras();
        trending = bundle.getString("trending");
        shows = bundle.getString("shows");

        if(trending!=null){
            toolbar_title.setText("Trending");
            tv_seeAllLable.setText("Trending");
        }else if(shows!=null){
            toolbar_title.setText("Shows");
            tv_seeAllLable.setText("Shows");
        }

        loadTrendingView(isGridView);

        changeViewTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGridView) {
                    isGridView = false;
                    loadTrendingView(isGridView);
                } else {
                    isGridView = true;
                    loadTrendingView(isGridView);
                }

            }
        });
    }

    private void loadTrendingView(boolean isGridView) {
        if (isGridView) {
            gridLayoutManager = new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(gridLayoutManager);
            mAdapter = new SeeAllAdapter(this, HomeFragment.imageList, isGridView);
            recyclerView.setAdapter(mAdapter);
            changeViewTrending.setBackgroundResource(R.drawable.ic_listview);

        } else {
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new SeeAllAdapter(this, HomeFragment.imageList, isGridView);
            recyclerView.setAdapter(mAdapter);
            changeViewTrending.setBackgroundResource(R.drawable.ic_view);
        }
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
