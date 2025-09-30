package com.own.babykickcounter;

import android.app.Application;
import com.own.babykickcounter.db.DBProxy;
import com.own.babykickcounter.util.SharedPreferenceUtil;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBProxy.getInstance().init(this);
        SharedPreferenceUtil.getInstance().init(this);
    }
}
