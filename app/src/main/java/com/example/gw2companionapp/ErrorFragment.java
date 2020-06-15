package com.example.gw2companionapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datastructures.ParsedDailyAchievements;

import org.jetbrains.annotations.NotNull;

public class ErrorFragment extends Fragment {
    private ParsedDailyAchievements parsedDailyAchievements;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       parsedDailyAchievements = ParsedDailyAchievements.getInstance(MainActivity.context);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState){
        TextView text = view.findViewById(R.id.error_text);
        text.setText(parsedDailyAchievements.getError());
    }
}