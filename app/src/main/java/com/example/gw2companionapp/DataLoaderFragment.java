package com.example.gw2companionapp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.fragment.app.Fragment;

import com.datastructures.ParsedDailyAchievements;

public class DataLoaderFragment{
    private LoaderListener listener;
    private HandlerThread handlerThread;

    /**
     * classes that want to be notified implement this
     */
    public interface LoaderListener {
        public void onLoaded();
    }

    /**
     * set the listener
     * @param listener LoadingFragment that wants to listen
     */
    public void addListener(LoaderListener listener) {
        this.listener = listener;
    }

    /**
     * creates a thread to wait till the dailies have loaded, then notify the listener
     */
    public void checkLoading() {
        handlerThread = new HandlerThread("LoaderListenerHandlerThread");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
        Runnable apiRunnable = new Runnable() {
            @Override
            public void run() {
                while (!ParsedDailyAchievements.checkDailiesLoaded()) {

                }
                listener.onLoaded();
            }
        };
        handler.post(apiRunnable);
    }

    /**
     * code to close the thread once this is no longer needed
     */
    public void closeHandler() {
        handlerThread.quit();
    }
}
