package com.own.babykickcounter.task;

import android.content.Context;
import android.os.AsyncTask;
import com.own.babykickcounter.R;
import com.own.babykickcounter.db.DBProxy;
import com.own.babykickcounter.model.TrackModel;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExportImportTask extends AsyncTask<Void, Void, Void> {

     Context mContext;
     WritableWorkbook mExportWorkbook;
     OnExportImportListener mListener;
     String mLocation;

    public interface OnExportImportListener {
        void onsuccessful();
    }

    public ExportImportTask(Context context, String str, OnExportImportListener onExportImportListener) {
        this.mContext = context;
        this.mLocation = str;
        this.mListener = onExportImportListener;
    }


    public Void doInBackground(Void... voidArr) {
        String format = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());
        File file = new File(this.mLocation + "/" + this.mContext.getString(R.string.export_file_name) + "_" + format + ".xls");
        if (file.exists()) {
            file.delete();
        }
        try {
            this.mExportWorkbook = Workbook.createWorkbook(file);
            WritableSheet createSheet = this.mExportWorkbook.createSheet(this.mContext.getString(R.string.export_sheet_name), 1);
            createSheet.addCell(new Label(0, 0, this.mContext.getString(R.string.export_col_1)));
            createSheet.addCell(new Label(1, 0, this.mContext.getString(R.string.export_col_2)));
            createSheet.addCell(new Label(2, 0, this.mContext.getString(R.string.export_col_3)));
            ArrayList<TrackModel> allTracks = DBProxy.getInstance().getAllTracks();
            int i = 0;
            while (i < allTracks.size()) {
                int i2 = i + 1;
                createSheet.addCell(new Label(0, i2, allTracks.get(i).getStart()));
                createSheet.addCell(new Label(1, i2, allTracks.get(i).getEnd()));
                createSheet.addCell(new Number(2, i2, (double) allTracks.get(i).getKicks()));
                i = i2;
            }
            this.mExportWorkbook.write();
            WritableWorkbook writableWorkbook = this.mExportWorkbook;
            if (writableWorkbook == null) {
                return null;
            }
            try {
                writableWorkbook.close();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (WriteException e2) {
                e2.printStackTrace();
                return null;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            WritableWorkbook writableWorkbook2 = this.mExportWorkbook;
            if (writableWorkbook2 == null) {
                return null;
            }
            try {
                writableWorkbook2.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            return null;
        } catch (WriteException e4) {
            e4.printStackTrace();
            WritableWorkbook writableWorkbook3 = this.mExportWorkbook;
            if (writableWorkbook3 == null) {
                return null;
            }
            try {
                writableWorkbook3.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            return null;
        } catch (Throwable th) {
            WritableWorkbook writableWorkbook4 = this.mExportWorkbook;
            if (writableWorkbook4 != null) {
                try {
                    writableWorkbook4.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                } catch (WriteException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
    }


    @Override
    public void onPostExecute(Void voidR) {
        super.onPostExecute(voidR);
        this.mListener.onsuccessful();


    }

}
