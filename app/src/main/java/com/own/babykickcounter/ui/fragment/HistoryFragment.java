package com.own.babykickcounter.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.own.babykickcounter.R;
import com.own.babykickcounter.db.DBProxy;
import com.own.babykickcounter.model.TrackModel;
import com.own.babykickcounter.obvervable.TrackDataChangeObserver;
import com.own.babykickcounter.task.ExportImportTask;
import com.own.babykickcounter.ui.adapter.TrackAdapter;
import com.own.babykickcounter.ui.dialog.DeleteDialog;
import com.own.babykickcounter.ui.dialog.ExportDialog;
import com.own.babykickcounter.ui.dialog.LoadingDialog;
import com.own.babykickcounter.util.Constants;
import com.own.babykickcounter.util.LogUtil;
import com.own.babykickcounter.util.SharedPreferenceUtil;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import pub.devrel.easypermissions.EasyPermissions;

public class HistoryFragment extends Fragment implements Observer, EasyPermissions.PermissionCallbacks {
    private final String tagg = getClass().getSimpleName();
    RecyclerView mLvHistory;
    RelativeLayout export;
    InterstitialAd interstitial;


    public void onPermissionsDenied(int i, List<String> list) {
        Log.d("ds","");
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TrackDataChangeObserver.getInstance().addObserver(this);
        View inflate = layoutInflater.inflate(R.layout.fragment_history, viewGroup, false);

        setHasOptionsMenu(true);

        interstitial = new InterstitialAd(getActivity());
        interstitial.setAdUnitId(getActivity().getString(R.string.g_inr));
        interstitial.loadAd(new AdRequest.Builder().build());
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                interstitial.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {

                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {

                // Code to be executed when the interstitial ad is closed.
            }
        });


        mLvHistory = (RecyclerView) inflate.findViewById(R.id.lv_history);
        export = (RelativeLayout) inflate.findViewById(R.id.topbar);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireStoragePermision(1);
            }
        });


        refreshViews();
        return inflate;
    }

    private void refreshViews() {
        this.mLvHistory.setAdapter(new TrackAdapter(DBProxy.getInstance().getAllTracks(), new TrackAdapter.Listener() {
            public void onItemclick(TrackModel trackModel) {
                Log.d("dss", "");
            }

            public void onDelete(final TrackModel trackModel) {
                if (SharedPreferenceUtil.getInstance().getBoolean(Constants.PREF_CONFIRM_DELETE)) {
                    DBProxy.getInstance().removeTrack(trackModel);
                } else {
                    new DeleteDialog(new DeleteDialog.Listener() {
                        public void onDelete() {
                            DBProxy.getInstance().removeTrack(trackModel);
                        }
                    }).show(HistoryFragment.this.getFragmentManager(), "delete");
                }
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TrackDataChangeObserver.getInstance().deleteObserver(this);
    }

    public void update(Observable observable, Object obj) {
        refreshViews();
    }


    private void backupData() {
        new ExportDialog(new ExportDialog.ONBACKUPLISTNER() {
            public void onBackup(String str) {
                File file = new File(str);
                if (!file.exists()) {
                    file.mkdirs();
                }
                final LoadingDialog loadingDialog = new LoadingDialog(HistoryFragment.this.getString(R.string.please_wait_export));
                loadingDialog.show(HistoryFragment.this.getFragmentManager(), "loading");
                loadingDialog.setCancelable(false);
                new ExportImportTask(HistoryFragment.this.getContext(), str, new ExportImportTask.OnExportImportListener() {
                    public void onsuccessful() {
                        loadingDialog.dismiss();
                        LoadingDialog loadingDialog = null;

                        if (loadingDialog != null) {
                            loadingDialog.dismiss();

                        }


                        Toast.makeText(HistoryFragment.this.getContext(), HistoryFragment.this.getString(R.string.toast_export_successful), Toast.LENGTH_SHORT).show();

                    }
                }).execute(new Void[0]);
            }
        }).show(getFragmentManager(), "export");
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        EasyPermissions.onRequestPermissionsResult(i, strArr, iArr, this);
    }

    public void onPermissionsGranted(int i, List<String> list) {
        if (i == 1) {
            backupData();
        }
    }

    private void requireStoragePermision(int i) {
        String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
        if (i != 1) {
            return;
        }
        if (EasyPermissions.hasPermissions(getContext(), strArr)) {
            LogUtil.i(this.tagg, "has permission");
            backupData();
            return;
        }
        LogUtil.i(this.tagg, "request permisison");
        EasyPermissions.requestPermissions((Fragment) this, getString(R.string.rationale_storage), 1, strArr);
    }
}
