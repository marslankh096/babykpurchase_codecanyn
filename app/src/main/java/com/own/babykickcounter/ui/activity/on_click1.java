package com.own.babykickcounter.ui.activity;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public final  class on_click1 implements BottomNavigationView.OnNavigationItemSelectedListener {


    private final  MainActivity f7a;

    public on_click1(MainActivity superMainActivity) {
        this.f7a = superMainActivity;
    }

    public final boolean onNavigationItemSelected(MenuItem menuItem) {
        return this.f7a.a1(menuItem);
    }
}
