package com.own.babykickcounter.task;

import android.content.Context;
import android.os.AsyncTask;
import com.own.babykickcounter.R;
import com.own.babykickcounter.db.DBProxy;
import com.own.babykickcounter.model.TrackModel;
import com.own.babykickcounter.util.Constants;
import com.own.babykickcounter.util.DateTimeFormatUtil;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.joda.time.DateTimeConstants;

public class BarchartDataTask extends AsyncTask {
    private ArrayList<BarEntry> entries;
    private int kicksPerSession = 0;
    private ArrayList<String> labels;
    private Context mContext;
    private Listener mListener;
    private int totalKicks = 0;
    private int totalSeconds = 0;
    private int totalSessions = 0;

    public interface Listener {
        void onloadbarchartdata(ArrayList<String> arrayList, ArrayList<BarEntry> arrayList2, String str, String str2, String str3, String str4);
    }

    public BarchartDataTask(Context context, Listener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.labels = new ArrayList<>();
        this.entries = new ArrayList<>();
    }


    public Object doInBackground(Object[] objArr) {
        int[] iArr = {0, 4, 8, 12, 16, 20};
        for (int i = 0; i < iArr.length; i++) {
            this.labels.add(iArr[i] + "-" + (iArr[i] + 4));
            int[] calculatePerTracks = calculatePerTracks(DBProxy.getInstance().getTracksInRangeHours(iArr[i], iArr[i] + 4));
            this.entries.add(new BarEntry((float) i, (float) calculatePerTracks[0]));
            this.totalKicks = this.totalKicks + calculatePerTracks[0];
            this.totalSessions = this.totalSessions + calculatePerTracks[1];
            this.totalSeconds += calculatePerTracks[2];
        }
        return null;
    }


    @Override
    public void onPostExecute(Object obj) {
        String str;
        super.onPostExecute(obj);
        int i = this.totalSeconds;
        if (i < 60) {
            str = this.totalSeconds + " " + this.mContext.getString(R.string.s);
        } else if (i < 3600) {
            str = (this.totalSeconds / 60) + " " + this.mContext.getString(R.string.m) + " " + (this.totalSeconds % 60) + " " + this.mContext.getString(R.string.s);
        } else {
            long j = (long) (i / DateTimeConstants.SECONDS_PER_HOUR);
            str = j + " " + this.mContext.getString(R.string.h) + " " + ((((long) i) - (3600 * j)) / 60) + " " + this.mContext.getString(R.string.m);
        }
        String str2 = str;
        int i2 = this.totalSessions;
        if (i2 != 0) {
            this.kicksPerSession = this.totalKicks / i2;
        }
        this.mListener.onloadbarchartdata(this.labels, this.entries, String.valueOf(this.totalKicks), str2, String.valueOf(this.totalSessions), String.valueOf(this.kicksPerSession));
    }

    private int[] calculatePerTracks(ArrayList<TrackModel> arrayList) {
        int size = arrayList.size();
        Iterator<TrackModel> it = arrayList.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            TrackModel next = it.next();
            i += next.getKicks();
            Date parse = DateTimeFormatUtil.getInstance().parse(next.getStart(), Constants.TIMEFORMAT);
            Date parse2 = DateTimeFormatUtil.getInstance().parse(next.getEnd(), Constants.TIMEFORMAT);
            i2 = (int) (((long) i2) + ((parse2.getTime() - parse.getTime()) / 1000));
        }
        return new int[]{i, size, i2};
    }
}
