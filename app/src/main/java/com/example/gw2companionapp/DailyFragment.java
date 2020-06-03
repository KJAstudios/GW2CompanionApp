package com.example.gw2companionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.datastructures.ParsedDailyAchievements;
import com.gw2apiparser.FailedHttpCallException;
import com.gw2apiparser.JsonParser;
import com.gw2apiparser.JsonParserFromFile;
import com.jsonclasses.DailiesClasses;

import org.jetbrains.annotations.NotNull;


public class DailyFragment extends Fragment {
    private ParsedDailyAchievements parsedAchievements;
    private String dailyType;
    private DailiesClasses.AllDailies allDailies;

    //TODO figure out a way to get quicker load times
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            dailyType = bundle.getString("dailyType");
        }
        try {
            allDailies = JsonParserFromFile.getDailiesFromFile(MainActivity.context);
        } catch (Exception e) {

        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateAchievementList(view);

        //click listener for button
        view.findViewById(R.id.back_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentManagerLayout, new MainDailyFragment());
                        ft.commit();
                    }
                }
        );
    }

    /**
     * populates the list of selectable achievements
     * @param view passing the view from OnViewCreated so it knows where to put the list
     */
    private void populateAchievementList(View view) {
        LinearLayout achievementList = view.findViewById(R.id.achievement_layout);
        for (DailiesClasses.DailyAchievement achievement : allDailies.getAchieveList(dailyType)) {
            DailiesClasses.SingleAchievement currentAchieve = null;
            Button button = new Button(MainActivity.context);
            button.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            try {
                currentAchieve = JsonParser.getAchievement(achievement.id);
            } catch (FailedHttpCallException e) {
                //TODO catch this more eloquently because this is stupid
                button.setText(e.getMessage());
                achievementList.addView(button);
            }
            if (currentAchieve != null) {
                button.setText(currentAchieve.getName());
                button.setTag(currentAchieve.getId());
                achievementList.addView(button);
            }
        }
    }
}
