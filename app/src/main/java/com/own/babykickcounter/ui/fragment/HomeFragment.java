package com.own.babykickcounter.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
//
import com.own.babykickcounter.R;
import com.own.babykickcounter.db.DBProxy;
import com.own.babykickcounter.model.TrackModel;
import com.own.babykickcounter.task.AverageKickTask;
import com.own.babykickcounter.util.Constants;
import com.own.babykickcounter.util.DateTimeFormatUtil;
import com.own.babykickcounter.util.LogUtil;
import com.own.babykickcounter.util.SharedPreferenceUtil;
import com.vaibhavlakhera.circularprogressview.CircularProgressView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    public final String tagg = getClass().getSimpleName();
    TextView mTvNote;
    CardView mBtnDone;
    CardView mBtnKick;
    CardView mBtnRefresh;
    CardView mBtnStart;

     Context mContext;

    RelativeLayout mControlView;

     String mFirstKickTime;

     int mKickCount = 0;

     String mLastKickTime;
    CircularProgressView mProgressView;
    TextView mTvFirst;
    TextView mTvLast;

    TextView mTvProgress;
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

    static  int statefrgment(HomeFragment homeFragment) {
        int i = homeFragment.mKickCount;
        homeFragment.mKickCount = i + 1;
        return i;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu1, menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title


        switch (item.getItemId()) {
            case R.id.rate:
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getActivity().getPackageName()));
                intent.addFlags(1208483840);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }

                return true;

            case R.id.share:

                final String appPackageName = getActivity().getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);

                return true;

            case R.id.privacy:
                String url = "https://www.google.com/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }



    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_home, viewGroup, false);





        mTvNote = (TextView) inflate.findViewById(R.id.tv_note);
        mBtnDone = (CardView) inflate.findViewById(R.id.btn_done);
        mBtnKick = (CardView) inflate.findViewById(R.id.btn_kick);
        mBtnRefresh = (CardView) inflate.findViewById(R.id.btn_refresh);
        mBtnStart = (CardView) inflate.findViewById(R.id.btn_start);
        mControlView = (RelativeLayout) inflate.findViewById(R.id.control_view);
        mProgressView = (CircularProgressView) inflate.findViewById(R.id.progressView);
        mTvFirst = (TextView) inflate.findViewById(R.id.tv_first);
        mTvLast = (TextView) inflate.findViewById(R.id.tv_last);
        mTvProgress = (TextView) inflate.findViewById(R.id.tv_progress);




        this.mContext = getContext();
        initViews();
        this.mBtnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomeFragment.this.mKickCount = 0;
                HomeFragment.this.mProgressView.setProgress(HomeFragment.this.mKickCount, false);
                TextView textView = HomeFragment.this.mTvProgress;
                textView.setText("" + HomeFragment.this.mKickCount);
                HomeFragment.this.mBtnStart.setVisibility(View.INVISIBLE);
                HomeFragment.this.mControlView.setVisibility(View.VISIBLE);
                SharedPreferenceUtil.getInstance().putBoolean(Constants.PREF_INPROGRESS, true);
                HomeFragment.this.mTvNote.setText(HomeFragment.this.getString(R.string.intruction_2));
            }
        });
        this.mBtnKick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomeFragment.statefrgment(HomeFragment.this);
                if (HomeFragment.this.mKickCount == 1) {
                    LogUtil.i(HomeFragment.this.tagg, "Start_session");
                    HomeFragment.this.mFirstKickTime = DateTimeFormatUtil.getInstance().format(new Date(), Constants.TIMEFORMAT);
                    HomeFragment homeFragment = HomeFragment.this;
                    homeFragment.mLastKickTime = homeFragment.mFirstKickTime;
                    HomeFragment.this.mTvFirst.setText(HomeFragment.this.sdf.format(new Date()));
                    HomeFragment.this.mTvLast.setText(HomeFragment.this.sdf.format(new Date()));
                } else {
                    HomeFragment.this.mLastKickTime = DateTimeFormatUtil.getInstance().format(new Date(), Constants.TIMEFORMAT);
                    HomeFragment.this.mTvLast.setText(HomeFragment.this.sdf.format(new Date()));
                }
                HomeFragment.this.mProgressView.setProgress(HomeFragment.this.mKickCount, false);
                TextView textView = HomeFragment.this.mTvProgress;
                textView.setText("" + HomeFragment.this.mKickCount);
                DBProxy.getInstance().insertTrack(new TrackModel(HomeFragment.this.mFirstKickTime, HomeFragment.this.mLastKickTime, HomeFragment.this.mKickCount));
                if (HomeFragment.this.mKickCount == 10) {
                    HomeFragment.this.mTvNote.setText(HomeFragment.this.getString(R.string.intruction_4));
                }
            }
        });
        this.mBtnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomeFragment.this.mTvLast.setText(HomeFragment.this.sdf.format(new Date()));
                DBProxy.getInstance().insertTrack(new TrackModel(HomeFragment.this.mFirstKickTime, HomeFragment.this.mLastKickTime, HomeFragment.this.mKickCount));
                HomeFragment.this.mControlView.setVisibility(View.INVISIBLE);
                HomeFragment.this.mBtnStart.setVisibility(View.VISIBLE);
                SharedPreferenceUtil.getInstance().putBoolean(Constants.PREF_INPROGRESS, false);
                new AverageKickTask(HomeFragment.this.mContext, new AverageKickTask.Listener() {
                    public void OnAverageCalculated(int i, String str) {
                        if ("".equals(str)) {
                            HomeFragment.this.mTvNote.setText(HomeFragment.this.getString(R.string.intruction_5));
                            return;
                        }
                        HomeFragment.this.mTvNote.setText(HomeFragment.this.getString(R.string.intruction_3, Integer.valueOf(i), str));
                    }
                }).execute(new Void[0]);
            }
        });
        this.mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HomeFragment.this.mKickCount = 0;
                HomeFragment.this.mTvFirst.setText("-");
                HomeFragment.this.mTvLast.setText("-");
                HomeFragment.this.mProgressView.setProgress(HomeFragment.this.mKickCount, false);
                TextView textView = HomeFragment.this.mTvProgress;
                textView.setText("" + HomeFragment.this.mKickCount);
                HomeFragment.this.mControlView.setVisibility(View.INVISIBLE);
                HomeFragment.this.mBtnStart.setVisibility(View.VISIBLE);
                DBProxy.getInstance().removeLastTrack();
                SharedPreferenceUtil.getInstance().putBoolean(Constants.PREF_INPROGRESS, false);
                new AverageKickTask(HomeFragment.this.mContext, new AverageKickTask.Listener() {
                    public void OnAverageCalculated(int i, String str) {
                        if ("".equals(str)) {
                            HomeFragment.this.mTvNote.setText(HomeFragment.this.getString(R.string.intruction_5));
                            return;
                        }
                        HomeFragment.this.mTvNote.setText(HomeFragment.this.getString(R.string.intruction_3, Integer.valueOf(i), str));
                    }
                }).execute(new Void[0]);
            }
        });
        return inflate;
    }

    private void initViews() {
        if (SharedPreferenceUtil.getInstance().getBoolean(Constants.PREF_FIRST_LAUNCH, true)) {
            this.mTvNote.setText(getString(R.string.intruction_1));
            SharedPreferenceUtil.getInstance().putBoolean(Constants.PREF_FIRST_LAUNCH, false);
        }
        TrackModel lastTrack = DBProxy.getInstance().getLastTrack();
        if (!SharedPreferenceUtil.getInstance().getBoolean(Constants.PREF_INPROGRESS) || lastTrack == null) {
            new AverageKickTask(this.mContext, new AverageKickTask.Listener() {
                public void OnAverageCalculated(int i, String str) {
                    String accessss = HomeFragment.this.tagg;
                    LogUtil.i(accessss, "average_kicks: " + i);
                    if ("".equals(str)) {
                        HomeFragment.this.mTvNote.setText(HomeFragment.this.getString(R.string.intruction_5));
                        return;
                    }
                    HomeFragment.this.mTvNote.setText(HomeFragment.this.getString(R.string.intruction_3, Integer.valueOf(i), str));
                }
            }).execute(new Void[0]);
        } else {
            this.mTvNote.setText(getString(R.string.intruction_2));
            this.mControlView.setVisibility(View.VISIBLE);
            this.mBtnStart.setVisibility(View.INVISIBLE);
            this.mFirstKickTime = lastTrack.getStart();
            this.mLastKickTime = lastTrack.getEnd();
            Date parse = DateTimeFormatUtil.getInstance().parse(this.mFirstKickTime, Constants.TIMEFORMAT);
            Date parse2 = DateTimeFormatUtil.getInstance().parse(this.mLastKickTime, Constants.TIMEFORMAT);
            this.mKickCount = lastTrack.getKicks();
            this.mTvFirst.setText(this.sdf.format(parse));
            this.mTvLast.setText(this.sdf.format(parse2));
            this.mTvNote.setText(getString(R.string.intruction_2));
        }
        this.mProgressView.setProgress(this.mKickCount, false);
        TextView textView = this.mTvProgress;
        textView.setText("" + this.mKickCount);
    }
}
