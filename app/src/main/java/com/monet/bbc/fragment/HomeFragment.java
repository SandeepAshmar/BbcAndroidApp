package com.monet.bbc.fragment;


import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monet.bbc.R;
import com.monet.bbc.adapter.SlidingImage_Adapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        /*viewPager = view.findViewById(R.id.pager);
        adapter = new SlidingImage_Adapter(this.getActivity(),IMAGES);
        viewPager.setAdapter(adapter);*/
        return view;
    }


}
