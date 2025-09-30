package com.own.babykickcounter.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;

import com.own.babykickcounter.R;

public class LoadingDialog extends DialogFragment {
    private String mMsg;
    TextView mTvMsg;

    public LoadingDialog(String str) {
        this.mMsg = str;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCancelable(false);

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.dialog_loading, viewGroup, false);

        mTvMsg = (TextView) inflate.findViewById(R.id.tv_msg);

        this.mTvMsg.setText(this.mMsg);
        return inflate;
    }
}
