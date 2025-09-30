package com.own.babykickcounter.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.own.babykickcounter.R;

public class ExportDialog extends DialogFragment {
    EditText mEdtExportLocation;

     ONBACKUPLISTNER mListener;

    public interface ONBACKUPLISTNER {
        void onBackup(String str);
    }

    public ExportDialog(ONBACKUPLISTNER ONBACKUPLISTNER) {
        this.mListener = ONBACKUPLISTNER;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.dialog_export, (ViewGroup) null);

        mEdtExportLocation = (EditText) inflate.findViewById(R.id.edt_export_location);



        builder.setView(inflate).setNegativeButton((CharSequence) getString(R.string.cancel), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ExportDialog.this.dismiss();
            }
        }).setPositiveButton((CharSequence) getString(R.string.export_data), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String obj = ExportDialog.this.mEdtExportLocation.getText().toString();
                if (obj.length() == 0) {
                    Toast.makeText(ExportDialog.this.getContext(), ExportDialog.this.getString(R.string.pick_export_location), Toast.LENGTH_SHORT).show();
                } else {
                    ExportDialog.this.mListener.onBackup(obj);
                }
                ExportDialog.this.dismiss();
            }

        });

        return builder.create();
    }
}
