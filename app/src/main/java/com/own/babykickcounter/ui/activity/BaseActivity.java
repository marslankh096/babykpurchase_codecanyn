package com.own.babykickcounter.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.own.babykickcounter.R;
import com.own.babykickcounter.util.Constants;
import com.own.babykickcounter.util.SharedPreferenceUtil;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (SharedPreferenceUtil.getInstance().getBoolean(Constants.PREF_DARK_THEME, false)) {
            setTheme(R.style.LightTheme);
        }

    }
}
