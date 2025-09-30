package com.own.babykickcounter.task;

import android.content.Context;
import android.os.AsyncTask;
import com.own.babykickcounter.R;
import com.own.babykickcounter.db.DBProxy;
import com.own.babykickcounter.model.TrackModel;
import com.own.babykickcounter.util.Constants;
import com.own.babykickcounter.util.DateTimeFormatUtil;
import java.util.Date;
import java.util.Iterator;

public class AverageKickTask extends AsyncTask<Void, Void, Void> {
    private Context mContext;
    private String mInTime = "";
    private int mKicks = 0;
    private Listener mListener;

    public interface Listener {
        void OnAverageCalculated(int i, String str);
    }

    public AverageKickTask(Context context, Listener listener) {
        this.mContext = context;
        this.mListener = listener;
    }


    public Void doInBackground(Void... voidArr) {
        Iterator<TrackModel> it = DBProxy.getInstance().getTracksToday().iterator();
        long j = 0;
        while (it.hasNext()) {
            TrackModel next = it.next();
            this.mKicks += next.getKicks();
            Date parse = DateTimeFormatUtil.getInstance().parse(next.getStart(), Constants.TIMEFORMAT);
            j += (DateTimeFormatUtil.getInstance().parse(next.getEnd(), Constants.TIMEFORMAT).getTime() - parse.getTime()) / 1000;
            if (j < 60) {
                this.mInTime = j + " " + this.mContext.getString(R.string.seconds);
            } else if (j < 3600) {
                this.mInTime = (j / 60) + " " + this.mContext.getString(R.string.minutes) + " " + (j % 60) + " " + this.mContext.getString(R.string.seconds);
            } else {
                long j2 = j / 3600;
                this.mInTime = j2 + " " + this.mContext.getString(R.string.hours) + " " + ((j - (3600 * j2)) / 60) + " " + this.mContext.getString(R.string.minutes);
            }
        }
        return null;
    }


    @Override
    public void onPostExecute(Void voidR) {
        super.onPostExecute(voidR);
        this.mListener.OnAverageCalculated(this.mKicks, this.mInTime);
    }
}
