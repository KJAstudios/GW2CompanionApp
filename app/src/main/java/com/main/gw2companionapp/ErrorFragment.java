package com.main.gw2companionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.datastructures.ParsedDailyAchievements;
import com.example.gw2companionapp.R;

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
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        //set the text to show what error occured
        TextView text = view.findViewById(R.id.error_text);
        text.setText(parsedDailyAchievements.getError());

        // set the click listener for the retry button
        Button retryButton = view.findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reload the ParsedDailyAchievements
                MainActivity.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ParsedDailyAchievements.getInstance(MainActivity.context);
                    }
                });
                //then kick it back to a LoadingFragment
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentManagerLayout, new LoadingFragment());
                ft.commit();
            }
        });


    }
}