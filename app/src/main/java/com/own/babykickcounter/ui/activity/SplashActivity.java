package com.own.babykickcounter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.own.babykickcounter.Ad_class;
import com.own.babykickcounter.R;

public class SplashActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash);

        Ad_class.load(SplashActivity.this);


        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.startMainActivity();
            }
        }, 1000);
    }


    public void startMainActivity() {
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }


}
