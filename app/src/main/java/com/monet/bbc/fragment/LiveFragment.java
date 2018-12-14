package com.monet.bbc.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;
import com.monet.bbc.activity.LiveVideoPlay;
import com.monet.bbc.adapter.LiveFragmentAdapter;
import com.monet.bbc.utils.AppUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends Fragment implements View.OnClickListener {

    private ImageView liveItemImage;
    private View view;
    private TextView optionsMenu, seeAllRecent;
    private RecyclerView recentLiveRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LiveFragmentAdapter liveFragmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_live, container, false);

        liveItemImage = view.findViewById(R.id.img_live_item);
        optionsMenu = view.findViewById(R.id.textViewOptions);
        seeAllRecent = view.findViewById(R.id.tv_liveSeeAll);
        recentLiveRecyclerView = view.findViewById(R.id.rv_liveScreen);

        Glide.with(this).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(liveItemImage);
        setRecentLivesAdapter();

        optionsMenu.setOnClickListener(this);
        seeAllRecent.setOnClickListener(this);

        liveItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LiveVideoPlay.class));
            }
        });

        return view;
    }

    private void setRecentLivesAdapter() {
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recentLiveRecyclerView.setLayoutManager(linearLayoutManager);
        liveFragmentAdapter = new LiveFragmentAdapter(getActivity(),HomeFragment.imageList);
        recentLiveRecyclerView.setAdapter(liveFragmentAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.textViewOptions:
                AppUtils.shortToast(getContext(), "Menu Button Clicked...");
                break;
            case R.id.tv_liveSeeAll:
                AppUtils.shortToast(getContext(), "see all clicked...");
                break;
            default:
                AppUtils.shortToast(getContext(), "Default Toast");
                break;
        }
    }
}
