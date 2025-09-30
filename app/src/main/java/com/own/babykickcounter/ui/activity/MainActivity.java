package com.own.babykickcounter.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.own.babykickcounter.Ad_class;
import com.own.babykickcounter.R;
import com.own.babykickcounter.ui.fragment.HistoryFragment;
import com.own.babykickcounter.ui.fragment.HomeFragment;
import com.own.babykickcounter.ui.fragment.InfoFragment;
import com.own.babykickcounter.ui.fragment.StatisticFragment;
import com.own.babykickcounter.util.LogUtil;


public class MainActivity extends AppCompatActivity {

    public final String tagg = getClass().getSimpleName();


    Context mContext;

    Toolbar toolbar;

    BottomNavigationView l;
    BottomNavigationMenuView meuvie;
    BottomNavigationItemView meuviebottom;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LogUtil.i(this.tagg, "onCreate");
        setContentView((int) R.layout.kick_activity_main);

        AdView mAdView = findViewById(R.id.adView);
        Ad_class.showbanner(mAdView);

        this.mContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.inflateMenu(R.menu.menu1);


        this.l = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        loadFragment(new HomeFragment());
        this.meuvie = (BottomNavigationMenuView) this.l.getChildAt(0);
        this.meuviebottom = (BottomNavigationItemView) this.meuvie.getChildAt(2);
        this.l.setOnNavigationItemSelectedListener(new on_click1(this));


    }

    public boolean a1(MenuItem menuItem) {
        Fragment fragment = new Fragment();
        switch (menuItem.getItemId()) {
            case R.id.menu_home:

                fragment = new HomeFragment();
                toolbar.setTitle(getString(R.string.app_name));


                break;
            case R.id.menu_history:

                fragment = new HistoryFragment();
                toolbar.setTitle(getString(R.string.history));


                break;
            case R.id.menu_statistic:

                fragment = new StatisticFragment();
                toolbar.setTitle(getString(R.string.chart));
                break;
            case R.id.menu_setting:

                fragment = new InfoFragment();
                toolbar.setTitle(getString(R.string.info));
                break;


            default:
                fragment = null;
                break;
        }
        return loadFragment(fragment);
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
        return true;
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
