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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentManagerLayout, new MainDailyFragment());
        ft.commit();

    }

    @Override
    protected void onDestroy() {
        handlerThread.quit();
        super.onDestroy();
    }
}
//MUST DOS BEFORE RELEASE
//TODO create bad internet state so if someone doesn't have internet it wont freak
//TODO create some sort of animation on loading screen to entertain users
//TODO UI add rewards
//TODO UI text formatting on achievement screen
//TODO UI achievement headers for informational organization
//TODO call achievements in one dump, instead of individual calls
//TODO add custom logos and splash screen
//TODO change header bar text to GW2 Dailies

//KNOWN BUGS
//fragment state not remembered when apps are switched