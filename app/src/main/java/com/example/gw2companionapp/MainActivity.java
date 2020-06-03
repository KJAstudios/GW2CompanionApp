package com.example.gw2companionapp;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.datastructures.ParsedDailyAchievements;
import com.gw2apiparser.FailedHttpCallException;
import com.gw2apiparser.JsonParser;
import com.trialclasses.CacheTest;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    private ParsedDailyAchievements dailyAchievements = null;
    private boolean dailiesLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        dailies are loaded here, as attempting to load them in the onCreate crashes the app
        they are then saved to a file in the cache
         */
        if (!dailiesLoaded) {
            try {
                CacheTest.saveDailies(context);
                dailiesLoaded = true;
            } catch (FailedHttpCallException e) {
                //TODO figure out a way to handle this eloquently
            }
        }

        //start the first fragment after the info for dailies are loaded
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentManagerLayout, new MainDailyFragment());
        ft.commit();

    }


    //old method, saving until using cache is confirmed faster
    private void loadDailies() {
        try {
            dailyAchievements = JsonParser.getDailies();
            dailiesLoaded = true;
        } catch (FailedHttpCallException e) {
            dailyAchievements = new ParsedDailyAchievements();
            dailyAchievements.setError(e.getMessage());
        }
    }
}
