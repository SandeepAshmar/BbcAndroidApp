package com.monet.bbc.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monet.bbc.R;
import com.monet.bbc.adapter.FavoriteAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    RecyclerView recyclerView;
    FavoriteAdapter favoriteAdapter;
    LinearLayoutManager linearLayoutManager;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerView = view.findViewById(R.id.rv_fav_list);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        favoriteAdapter = new FavoriteAdapter(getActivity(), 50);
        recyclerView.setAdapter(favoriteAdapter);


        return view;
    }

}
