package com.own.babykickcounter.model;

public class TrackModel {
    private String end;
    private int kicks;
    private String start;

    public TrackModel() {
    }

    public TrackModel(String str, String str2, int i) {
        this.start = str;
        this.end = str2;
        this.kicks = i;
    }

    public String getStart() {
        return this.start;
    }

    public TrackModel setStart(String str) {
        this.start = str;
        return this;
    }

    public String getEnd() {
        return this.end;
    }

    public TrackModel setEnd(String str) {
        this.end = str;
        return this;
    }

    public int getKicks() {
        return this.kicks;
    }

    public TrackModel setKicks(int i) {
        this.kicks = i;
        return this;
    }
}
