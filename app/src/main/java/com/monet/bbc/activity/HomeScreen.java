package com.monet.bbc.activity;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.monet.bbc.R;
import com.monet.bbc.fragment.FavouriteFragment;
import com.monet.bbc.fragment.HomeFragment;
import com.monet.bbc.fragment.LiveFragment;
import com.monet.bbc.fragment.PlaylistFragment;
import com.monet.bbc.fragment.TrendingFragment;

import java.lang.reflect.Field;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private FavouriteFragment favouriteFragment;
    private HomeFragment homeFragment;
    private LiveFragment liveFragment;
    private PlaylistFragment playlistFragment;
    private TrendingFragment trendingFragment;

    private Toolbar toolbar;
    private TextView tv_navigationPoints, tv_navigationName, tv_navigationPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setRightSideDrawer();
    }

    private void setRightSideDrawer() {
        Menu menu = bottomNavigationView.getMenu();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tv_navigationPoints = headerView.findViewById(R.id.tv_navigationPoints);
        tv_navigationName = headerView.findViewById(R.id.tv_navigationName);
        tv_navigationPlace = headerView.findViewById(R.id.tv_navigationPlace);

        tv_navigationPoints.setText("3695");
        tv_navigationName.setText("Sandeep Malik");
        tv_navigationPlace.setText("Sonepat, Haryana");

        navigationView.setNavigationItemSelectedListener(this);

        menu.add(Menu.NONE, R.id.nav_home, Menu.NONE, "Home")
                .setIcon(R.mipmap.live);
        menu.add(Menu.NONE, R.id.nav_live, Menu.NONE, "Live")
                .setIcon(R.mipmap.live);
        menu.add(Menu.NONE, R.id.nav_trending, Menu.NONE, "Trending")
                .setIcon(R.mipmap.live);
        menu.add(Menu.NONE, R.id.nav_playlist, Menu.NONE, "Playlist")
                .setIcon(R.mipmap.live);
        menu.add(Menu.NONE, R.id.nav_favourite, Menu.NONE, "Favourite")
                .setIcon(R.mipmap.live);

        favouriteFragment = new FavouriteFragment();
        homeFragment = new HomeFragment();
        liveFragment = new LiveFragment();
        playlistFragment = new PlaylistFragment();
        trendingFragment = new TrendingFragment();

        setFragment(homeFragment);
        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            /*shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }*/
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    private void setFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_home) {
            setFragment(homeFragment);
        } else if (id == R.id.nav_live) {
            setFragment(liveFragment);
        } else if (id == R.id.nav_trending) {
            setFragment(trendingFragment);
        } else if (id == R.id.nav_playlist) {
            setFragment(playlistFragment);
        } else if (id == R.id.nav_favourite) {
            setFragment(favouriteFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
