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
        /*
        dailies are loaded here, as attempting to load them in the onCreate crashes the app
         */
        Runnable apiRunnable = new Runnable() {
            @Override
            public void run() {
                ParsedDailyAchievements.getInstance(context);
            }
        };
        handler.post(apiRunnable);


        //start the first fragment after the info for dailies are loaded
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentManagerLayout, new LoadingFragment());
        ft.commit();

    }

    @Override
    protected void onDestroy() {
        handlerThread.quit();
        super.onDestroy();
    }
}
//MUST DOS BEFORE RELEASE
//TODO check achievement level display to see if fixed
//TODO UI improve rewards display for achievements
//TODO UI text formatting on achievement screen
//TODO UI achievement headers for informational organization
//TODO add custom logos and splash screen
//TODO check all errors being thrown by commit screen, and deal with them

//KNOWN BUGS
//fragment state not remembered when apps are switched
//pve dailies has duplicate, for some reason duplicate has different levels, but fails to function