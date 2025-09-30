package com.own.babykickcounter.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.own.babykickcounter.Ad_class;
import com.own.babykickcounter.R;

public class StartActivity extends BaseActivity {

    RelativeLayout startrel;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_start);

        FrameLayout frameLayout = findViewById(R.id.native_frame);
        Ad_class.refreshAd(frameLayout, StartActivity.this);

        startrel = (RelativeLayout) findViewById(R.id.startrel);
        startrel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Ad_class.showFullAd(StartActivity.this, new Ad_class.Onlisoner() {
                    @Override
                    public void click() {
                        Ad_class.showFullAd(StartActivity.this, new Ad_class.Onlisoner() {
                            @Override
                            public void click() {
                                startActivity(new Intent(StartActivity.this, MainActivity.class));
                            }});

                    }});

            }
        });


    }

    public void exitdialog(final Activity activity) {
        if (activity != null) {
            View inflate = LayoutInflater.from(activity).inflate(R.layout.exit_confirm_dialog, (ViewGroup) null);


            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(1);
            dialog.setContentView(inflate);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            FrameLayout frameLayout = dialog.findViewById(R.id.native_frame);
            Ad_class.refreshAd(frameLayout, StartActivity.this);



            ((RelativeLayout) inflate.findViewById(R.id.cancel_btn)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            ((RelativeLayout) inflate.findViewById(R.id.ok_btn)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                    finish();
                    System.exit(0);



                }
            });
            dialog.show();
            dialog.getWindow().setLayout(-1, -2);
        }
    }

    @Override
    public void onBackPressed() {
        exitdialog(StartActivity.this);
    }
}
