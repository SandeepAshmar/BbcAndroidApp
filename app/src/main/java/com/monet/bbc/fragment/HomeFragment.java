package com.monet.bbc.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monet.bbc.R;
import com.monet.bbc.activity.HomeScreen;
import com.monet.bbc.activity.SeeAllActivity;
import com.monet.bbc.adapter.HomePlayListAdapter;
import com.monet.bbc.adapter.HomeShowsAdapter;
import com.monet.bbc.adapter.HomeTrendingAdapter;
import com.monet.bbc.adapter.ImageSliderHome;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ViewPager mPager;
    private int currentPage = 0;
    private String one = "https://images.pexels.com/photos/414171/pexels-photo-414171.jpeg";
    private String two = "https://cdn.fstoppers.com/styles/med-16-9/s3/lead/2017/08/iceland-unique-compositions-landscape-photography.jpg";
    private String three = "https://lh6.googleusercontent.com/proxy/x2cf-GR30wkgwbG0Z_Cqq7ew7jvuzuSLQ5q4Xbk7r2vAlSAFgYTD_-XNQETsHIwgzBOGnSFGP3XhNGNq1veyNRMugXEwRNzVN35yMLWMVPCUbFvAzA--u_6GFOTXy4iVpIfkSSfG10wqhoQlzzo4=s0-d";
    private String four = "https://2dhnizrxqvv1awj231eodql1-wpengine.netdna-ssl.com/wp-content/uploads/2017/06/landscape-photography-weather.jpg";
    private String five = "http://www.michaelleadbetter.com.au/portfolio/Landscape-Photography-Brisbane.jpg";
    private String six = "https://png.pngtree.com/thumb_back/fh260/back_pic/03/72/94/8457b9a65caa197.jpg";
    public static ArrayList<String> imageList = new ArrayList<>();
    private HomeShowsAdapter mShowsAdapter;
    private LinearLayoutManager showsLayoutManager;
    private RecyclerView rv_showsHome, rv_trendingHome, rv_playListHome;
    private HomeTrendingAdapter mTrendAdapter;
    private LinearLayoutManager trendLayoutManager;
    private HomePlayListAdapter mPlayListAdapter;
    private LinearLayoutManager playListLayoutManager;
    private TextView tv_trendingSeeAll, tv_shwosSeeAll;
    private Bundle bundle;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rv_showsHome = view.findViewById(R.id.rv_showsHome);
        rv_trendingHome = view.findViewById(R.id.rv_trendingHome);
        rv_playListHome = view.findViewById(R.id.rv_playListHome);
        tv_trendingSeeAll = view.findViewById(R.id.tv_trendingSeeAll);
        tv_shwosSeeAll = view.findViewById(R.id.tv_shwosSeeAll);

        imageList.clear();
        imageList.add(one);
        imageList.add(two);
        imageList.add(three);
        imageList.add(four);
        imageList.add(five);
        imageList.add(six);

        imageSliderAdapter(view);
        setShowsAdapter();
        setTrendingAdapter();
        setPlayListAdapter();
        bundle = new Bundle();

        tv_trendingSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SeeAllActivity.class);
                bundle.putString("trending", "trending");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                bundle.clear();
            }
        });

        tv_shwosSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SeeAllActivity.class);
                bundle.putString("shows", "shows");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                bundle.clear();
            }
        });

        return view;
    }

    private void setPlayListAdapter() {
        playListLayoutManager = new LinearLayoutManager(getActivity());
        rv_playListHome.setLayoutManager(playListLayoutManager);
        mPlayListAdapter = new HomePlayListAdapter(getActivity(), imageList);
        rv_playListHome.setAdapter(mPlayListAdapter);
    }

    private void setTrendingAdapter() {
        trendLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_trendingHome.setLayoutManager(trendLayoutManager);
        mTrendAdapter = new HomeTrendingAdapter(getActivity(), imageList);
        rv_trendingHome.setAdapter(mTrendAdapter);
    }

    private void setShowsAdapter() {
        showsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_showsHome.setLayoutManager(showsLayoutManager);
        mShowsAdapter = new HomeShowsAdapter(getActivity(), imageList);
        rv_showsHome.setAdapter(mShowsAdapter);
    }

    private void imageSliderAdapter(View view) {

        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(new ImageSliderHome(getActivity(),imageList));
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == imageList.size()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 2500, 2500);
    }

}
