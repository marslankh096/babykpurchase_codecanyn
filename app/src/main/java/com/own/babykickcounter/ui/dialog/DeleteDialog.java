package com.own.babykickcounter.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.own.babykickcounter.R;
import com.own.babykickcounter.util.Constants;
import com.own.babykickcounter.util.LogUtil;
import com.own.babykickcounter.util.SharedPreferenceUtil;

public class DeleteDialog extends DialogFragment {
    private final String tagg = getClass().getSimpleName();
    CheckBox mCkb;

    public Listener mListener;

    public interface Listener {
        void onDelete();
    }

    public DeleteDialog() {
    }

    public DeleteDialog(Listener listener) {
        this.mListener = listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.dialog_delete, (ViewGroup) null);

        mCkb = (CheckBox) inflate.findViewById(R.id.ckb);

        builder.setView(inflate).setNegativeButton((CharSequence) getString(R.string.cancel), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                DeleteDialog.this.dismiss();
            }
        }).setPositiveButton((CharSequence) getString(R.string.ok), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                DeleteDialog.this.mListener.onDelete();
                SharedPreferenceUtil.getInstance().putBoolean(Constants.PREF_CONFIRM_DELETE, DeleteDialog.this.mCkb.isChecked());
                DeleteDialog.this.dismiss();
            }
        });
        AlertDialog create = builder.create();
        LogUtil.i(this.tagg, "onCreateDialog");
        return create;
    }
}
