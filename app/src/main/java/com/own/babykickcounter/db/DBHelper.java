package com.own.babykickcounter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.own.babykickcounter.model.TrackModel;
import com.own.babykickcounter.obvervable.TrackDataChangeObserver;
import com.own.babykickcounter.util.LogUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "kick.db";
    public static final String TABLE_KICK = "tb_kick";


    private final String tagg = getClass().getName();

    protected DBHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE tb_kick(start_time TEXT PRIMARY KEY,end_time TEXT,kick INTEGER)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        onCreate(sQLiteDatabase);
    }


    public long insertTrack(TrackModel trackModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("start_time", trackModel.getStart());
        contentValues.put("end_time", trackModel.getEnd());
        contentValues.put("kick", Integer.valueOf(trackModel.getKicks()));
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String str = this.tagg;
        LogUtil.i(str, "insertTrack " + trackModel.getKicks());
        long insertWithOnConflict = writableDatabase.insertWithOnConflict(TABLE_KICK, (String) null, contentValues, 5);
        TrackDataChangeObserver.getInstance().notifyChanged();
        return insertWithOnConflict;
    }


    public long removeLastTrack() {
        TrackModel lastTrack = getLastTrack();
        if (lastTrack == null) {
            return -1;
        }
        long delete = (long) getWritableDatabase().delete(TABLE_KICK, "start_time =?", new String[]{lastTrack.getStart()});
        TrackDataChangeObserver.getInstance().notifyChanged();
        return delete;
    }


    public long removeTrack(TrackModel trackModel) {
        if (trackModel == null) {
            return -1;
        }
        long delete = (long) getWritableDatabase().delete(TABLE_KICK, "start_time =?", new String[]{trackModel.getStart()});
        TrackDataChangeObserver.getInstance().notifyChanged();
        return delete;
    }


    public TrackModel getLastTrack() {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM tb_kick", (String[]) null);
            rawQuery.moveToLast();
            String string = rawQuery.getString(rawQuery.getColumnIndex("start_time"));
            String string2 = rawQuery.getString(rawQuery.getColumnIndex("end_time"));
            int i = rawQuery.getInt(rawQuery.getColumnIndex("kick"));
            String str = this.tagg;
            LogUtil.i(str, "getLastTrack: " + i);
            return new TrackModel().setStart(string).setEnd(string2).setKicks(i);
        } catch (Exception e) {
            String str2 = this.tagg;
            LogUtil.e(str2, "getLastTrack " + e.getMessage());
            return null;
        }
    }


    public ArrayList<TrackModel> getTracksToday() {
        ArrayList<TrackModel> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar instance = Calendar.getInstance();
            instance.add(5, 1);
            Cursor query = readableDatabase.query(TABLE_KICK, (String[]) null, "start_time BETWEEN ? AND ?", new String[]{simpleDateFormat.format(date), simpleDateFormat.format(instance.getTime())}, (String) null, (String) null, (String) null, (String) null);
            if (query != null && query.getCount() > 0) {
                if (query.moveToFirst()) {
                    do {
                        String string = query.getString(query.getColumnIndex("start_time"));
                        String string2 = query.getString(query.getColumnIndex("end_time"));
                        int i = query.getInt(query.getColumnIndex("kick"));
                        String str = this.tagg;
                        LogUtil.i(str, "getTracksToday: " + i);
                        arrayList.add(new TrackModel().setStart(string).setEnd(string2).setKicks(i));
                    } while (query.moveToNext());
                }
                query.close();
            }
        } catch (Exception e) {
            String str2 = this.tagg;
            LogUtil.e(str2, "getTracksToday " + e.getMessage());
        }
        return arrayList;
    }


    public ArrayList<TrackModel> getTracksInRangeHours(int i, int i2) {
        int i3 = i2;
        String str = this.tagg;
        LogUtil.i(str, "getTracksInRangeHours: " + i + " " + i3);
        ArrayList<TrackModel> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        try {
            Cursor query = readableDatabase.query(TABLE_KICK, (String[]) null, "strftime('%H',start_time) BETWEEN ? AND ?", new String[]{String.format("%02d", new Object[]{Integer.valueOf(i)}), String.format("%02d", new Object[]{Integer.valueOf(i3 - 1)})}, (String) null, (String) null, (String) null, (String) null);
            if (query != null && query.getCount() > 0) {
                if (query.moveToFirst()) {
                    do {
                        String string = query.getString(query.getColumnIndex("start_time"));
                        String string2 = query.getString(query.getColumnIndex("end_time"));
                        int i4 = query.getInt(query.getColumnIndex("kick"));
                        String str2 = this.tagg;
                        LogUtil.i(str2, "getTracksInRangeHours: " + string + " " + i4);
                        arrayList.add(new TrackModel().setStart(string).setEnd(string2).setKicks(i4));
                    } while (query.moveToNext());
                }
                query.close();
            }
        } catch (Exception e) {
            String str3 = this.tagg;
            LogUtil.e(str3, "getTracksInRangeHours " + e.getMessage());
        }
        return arrayList;
    }


    public ArrayList<TrackModel> getAllTracks() {
        ArrayList<TrackModel> arrayList = new ArrayList<>();
        try {
            Cursor query = getReadableDatabase().query(TABLE_KICK, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "start_time DESC", (String) null);
            if (query != null && query.getCount() > 0) {
                if (query.moveToFirst()) {
                    do {
                        String string = query.getString(query.getColumnIndex("start_time"));
                        String string2 = query.getString(query.getColumnIndex("end_time"));
                        int i = query.getInt(query.getColumnIndex("kick"));
                        String str = this.tagg;
                        LogUtil.i(str, "getAllTracks: " + string + " " + i);
                        arrayList.add(new TrackModel().setStart(string).setEnd(string2).setKicks(i));
                    } while (query.moveToNext());
                }
                query.close();
            }
        } catch (Exception e) {
            String str2 = this.tagg;
            LogUtil.e(str2, "getAllTracks " + e.getMessage());
        }
        return arrayList;
    }
}
