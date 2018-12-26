package com.monet.bbc.activity;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.monet.bbc.R;
import com.monet.bbc.fragment.FavouriteFragment;
import com.monet.bbc.fragment.HomeFragment;
import com.monet.bbc.fragment.LiveFragment;
import com.monet.bbc.fragment.PlaylistFragment;
import com.monet.bbc.fragment.TrendingFragment;
import com.monet.bbc.utils.AppPreference;

import java.lang.reflect.Field;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("NewApi")
public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private FavouriteFragment favouriteFragment;
    private HomeFragment homeFragment;
    private LiveFragment liveFragment;
    private PlaylistFragment playlistFragment;
    private TrendingFragment trendingFragment;
    private CircleImageView img_navProfile;
    private DonutProgress dp_navProfile;
    private LinearLayout ll_navLogout;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TextView tv_navScore, tv_navRewards, tv_navRanking, tv_navName, tv_navPlace;
    private NavigationView navigationView;
    private int navItemClicked = -1;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ll_navLogout = findViewById(R.id.ll_navLogout);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ll_navLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure?");
                builder.setCancelable(false);
                builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AppPreference.clearAllPreferences(HomeScreen.this);
                        startActivity(new Intent(HomeScreen.this, LoginScreen.class));
                        finish();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });

        setRightSideDrawer();
    }

    private void setRightSideDrawer() {

        Menu menu = bottomNavigationView.getMenu();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tv_navName = headerView.findViewById(R.id.tv_navName);
        tv_navPlace = headerView.findViewById(R.id.tv_navPlace);
        dp_navProfile = headerView.findViewById(R.id.dp_navProfile);
        img_navProfile = headerView.findViewById(R.id.img_navProfile);
        tv_navScore = headerView.findViewById(R.id.tv_navScore);
        tv_navRewards = headerView.findViewById(R.id.tv_navRewards);
        tv_navRanking = headerView.findViewById(R.id.tv_navRanking);
        tv_navName.setText("Sandeep Malik");
        tv_navPlace.setText("Sonepat, Haryana");
        dp_navProfile.setText("");

        navigationView.setNavigationItemSelectedListener(this);

        menu.add(Menu.NONE, R.id.nav_home, Menu.NONE, "Home")
                .setIcon(R.drawable.ic_home);
        menu.add(Menu.NONE, R.id.nav_live, Menu.NONE, "Live")
                .setIcon(R.drawable.ic_live);
        menu.add(Menu.NONE, R.id.nav_trending, Menu.NONE, "Trending")
                .setIcon(R.drawable.ic_trending);
        menu.add(Menu.NONE, R.id.nav_playlist, Menu.NONE, "Playlist")
                .setIcon(R.drawable.ic_playlist);
        menu.add(Menu.NONE, R.id.nav_favourite, Menu.NONE, "Favourite")
                .setIcon(R.drawable.ic_favourite);

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
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
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
        final int id = item.getItemId();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (id == R.id.nav_home) {
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

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                changeActivity(id);
            }
        };
        handler.postDelayed(runnable, 350);

        return true;
    }

    private void changeActivity(int id) {
        if (id == R.id.nav_profile) {
            navItemClicked = 0;
            Intent intent = new Intent(this, ProfileScreen.class);
            startActivity(intent);
        } else if (id == R.id.nav_rewards) {
            Intent intent = new Intent(this, RewardsScreen.class);
            startActivity(intent);
            navItemClicked = 1;
        } else if (id == R.id.nav_leaderboard) {
            Intent intent = new Intent(this, LeaderboardScreen.class);
            startActivity(intent);
            navItemClicked = 2;
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, SettingScreen.class);
            startActivity(intent);
            navItemClicked = 3;
        }
    }

    @Override
    protected void onResume() {
        if (AppPreference.getImageURL(this).isEmpty()) {
            Glide.with(this).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(img_navProfile);
        } else {
            Glide.with(this).load(AppPreference.getImageURL(this)).into(img_navProfile);
        }

        if (navItemClicked != -1) {
            navigationView.getMenu().getItem(navItemClicked).setChecked(false);
            navItemClicked = -1;
            navigationView.setSelected(true);
        }

        super.onResume();
    }

}
