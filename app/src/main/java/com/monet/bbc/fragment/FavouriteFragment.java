package com.monet.bbc.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.monet.bbc.R;
import com.monet.bbc.adapter.FavoriteAdapter;

import static com.monet.bbc.utils.AppUtils.runFallDownAnimation;
import static com.monet.bbc.utils.AppUtils.runRightSideAnimation;

public class FavouriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private View view;
    private ImageView changeView;
    private boolean isGridView = true;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        changeView = view.findViewById(R.id.img_change_view);
        recyclerView = view.findViewById(R.id.rv_fav_list);

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
            gridLayoutManager = new GridLayoutManager(getActivity(),2);
            recyclerView.setLayoutManager(gridLayoutManager);
            favoriteAdapter = new FavoriteAdapter(getActivity(), 50, isGridView);
            recyclerView.setAdapter(favoriteAdapter);
            changeView.setBackgroundResource(R.drawable.ic_listview);

        } else {
            linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            favoriteAdapter = new FavoriteAdapter(getActivity(), 50, isGridView);
            recyclerView.setAdapter(favoriteAdapter);
            changeView.setBackgroundResource(R.drawable.ic_view);
        }
    }
}
