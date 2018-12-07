package com.picgure.api.thread;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * @author Jeet Prakash
 * */
public class DownloadProgress extends Observable {

    private int objectCount;
    private List<DownloadResult> results = new CopyOnWriteArrayList<>();

    public DownloadProgress(int objectCount) {
        this.objectCount = objectCount;
    }

    public void addResult(Object result) {
        results.add((DownloadResult) result);
        setChanged();
        notifyObservers(result);
    }

    public List<DownloadResult> getResults() {
        return this.results;
    }

    public int getObjectCount() {
        return this.objectCount;
    }
}
