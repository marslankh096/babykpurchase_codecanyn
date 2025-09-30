package com.own.babykickcounter.obvervable;

import java.util.Observable;

public class TrackDataChangeObserver extends Observable {
    private static TrackDataChangeObserver instance;

    private TrackDataChangeObserver() {
    }

    public static TrackDataChangeObserver getInstance() {
        if (instance == null) {
            instance = new TrackDataChangeObserver();
        }
        return instance;
    }

    public void notifyChanged() {
        setChanged();
        notifyObservers();
    }
}
