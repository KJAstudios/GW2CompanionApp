package com.example.gw2companionapp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.fragment.app.Fragment;

import com.datastructures.ParsedDailyAchievements;

public class DataLoaderFragment extends Fragment {
    private LoaderListener listener;

    /**
     * classes that want to be notified implement this
     */
    public interface LoaderListener {
        public void onLoaded();
    }

    public void addListener(LoaderListener listener) {
        this.listener = listener;
    }

    public void checkLoading() {
        HandlerThread handlerThread = new HandlerThread("LoaderListenerHandlerThread");
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
}
