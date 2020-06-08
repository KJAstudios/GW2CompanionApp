package com.example.gw2companionapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.datastructures.ParsedDailyAchievements;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    private ParsedDailyAchievements dailyAchievements = null;
    private boolean dailiesLoaded;
    public static Handler handler;
    private HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        handlerThread = new HandlerThread("ApiHandlerThread");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        handler = new Handler(looper);


    }

    @Override
    protected void onStart() {
        super.onStart();

        //show LoadingFragment till info is loaded
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentManagerLayout, new LoadingFragment());
        ft.commit();
        /*
        dailies are loaded here, as attempting to load them in the onCreate crashes the app
         */
        Runnable apiRunnable = new Runnable() {
            @Override
            public void run() {
                dailyAchievements = ParsedDailyAchievements.getInstance(context);
            }
        };
        handler.post(apiRunnable);


        //start the first fragment after the info for dailies are loaded
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentManagerLayout, new MainDailyFragment());
        ft.commit();

    }

    @Override
    protected void onDestroy() {
        handlerThread.quit();
        super.onDestroy();
    }


    //old method, saving until using cache is confirmed faster
    /*
    private void loadDailies() {
        try {
            dailyAchievements = JsonParser.getDailies();
            dailiesLoaded = true;
        } catch (FailedHttpCallException e) {
            dailyAchievements = new ParsedDailyAchievements();
            dailyAchievements.setError(e.getMessage());
        }
    }
     */
}
