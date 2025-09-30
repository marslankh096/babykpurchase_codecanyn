package com.own.babykickcounter.db;

import android.content.Context;
import com.own.babykickcounter.model.TrackModel;
import java.util.ArrayList;

public class DBProxy {
    private static DBProxy instance;

    private DBHelper dbHelper;

    private DBProxy() {
    }

    public static synchronized DBProxy getInstance() {
        DBProxy dBProxy;
        synchronized (DBProxy.class) {
            if (instance == null) {
                instance = new DBProxy();
            }
            dBProxy = instance;
        }
        return dBProxy;
    }

    public void init(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long insertTrack(TrackModel trackModel) {
        return this.dbHelper.insertTrack(trackModel);
    }

    public TrackModel getLastTrack() {
        return this.dbHelper.getLastTrack();
    }

    public long removeTrack(TrackModel trackModel) {
        return this.dbHelper.removeTrack(trackModel);
    }

    public long removeLastTrack() {
        return this.dbHelper.removeLastTrack();
    }

    public ArrayList<TrackModel> getTracksInRangeHours(int i, int i2) {
        return this.dbHelper.getTracksInRangeHours(i, i2);
    }

    public ArrayList<TrackModel> getTracksToday() {
        return this.dbHelper.getTracksToday();
    }

    public ArrayList<TrackModel> getAllTracks() {
        return this.dbHelper.getAllTracks();
    }
}
