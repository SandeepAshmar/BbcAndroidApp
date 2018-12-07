package com.monet.bbc.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.monet.bbc.R;
import com.monet.bbc.adapter.PlayListAdapter;
import com.monet.bbc.adapter.TrendingAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingFragment extends Fragment {

    private RecyclerView recyclerView;
    private TrendingAdapter trendingAdapter;
    private LinearLayoutManager linearLayoutManager;
    private View view;
    private ImageView changeView;
    private boolean isGridView = false;

    public TrendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_trending, container, false);
        changeView = view.findViewById(R.id.img_change_view);
        recyclerView = view.findViewById(R.id.rv_trending);
        loadView(isGridView);

        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGridView) {
                    isGridView = false;
                    loadView(isGridView);
                } else {
                    isGridView = true;
                    loadView(isGridView);
                }

            }
        });
        return view;
    }

    private void loadView(boolean isGridView) {

        if (isGridView) {
            /*gridLayoutManager = new GridLayoutManager(getActivity(),2);
            recyclerView.setLayoutManager(gridLayoutManager);
            playListAdapter = new PlayListAdapter(getActivity(), 50, isGridView);
            recyclerView.setAdapter(playListAdapter);
            changeView.setBackgroundResource(R.drawable.ic_listview);*/

        } else {
            linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            trendingAdapter = new TrendingAdapter(getActivity(), 50, isGridView);
            recyclerView.setAdapter(trendingAdapter);
            changeView.setBackgroundResource(R.drawable.ic_view);
        }
    }

}
