package com.example.gw2companionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.datastructures.FullAchievement;
import com.datastructures.ParsedDailyAchievements;

import org.jetbrains.annotations.NotNull;


public class DailyFragment extends Fragment {
    private ParsedDailyAchievements parsedAchievements;
    private String dailyType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        dailyType = bundle.getString("dailyType");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //creates the list of achievements
        //if the daily achievements don't exist, throw it to the error screen
        if (ParsedDailyAchievements.checkDailiesFailed() == false) {
            populateAchievementList(view);
        } else {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentManagerLayout, new ErrorFragment());
            ft.commit();
        }


        //click listener for back button
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
     *
     * @param view passing the view from OnViewCreated so it knows where to put the list
     */
    private void populateAchievementList(View view) {
        parsedAchievements = ParsedDailyAchievements.getInstance(MainActivity.context);
        LinearLayout achievementList = view.findViewById(R.id.achievement_layout);
        for (int i = 0; i < parsedAchievements.getAchieveList(dailyType).size(); i++) {
            FullAchievement achievement = parsedAchievements.getAchieveList(dailyType).get(i);
            Button button = createButton(i, achievement);
            achievementList.addView(button);
            setButtonListener(i);
        }
    }

    /**
     * creates the button with achievement info
     *
     * @param achievement what achievement to display the name of
     * @return
     */
    private Button createButton(int i, FullAchievement achievement) {
        Button button = new Button(MainActivity.context);
        button.setId(i);
        button.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        String text = achievement.getName();
        text += "\n";
        if(achievement.getLevelMax() == achievement.getLevelMin()){
            text = text + "Level " + achievement.getLevelMax();
        }
        else {
            text = text + "Level " + achievement.getLevelMin()
                    + " - " + achievement.getLevelMax();
        }
        button.setText(text);
        return button;
    }

    /**
     * sets the click listeners for the achievement list by bundling required info for fragment
     *
     * @param id single digit id of achievement in list of achievements
     */
    private void setButtonListener(final int id) {
        getView().findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AchievementFragment fragment = new AchievementFragment();
                Bundle bundle = new Bundle();
                bundle.putString("dailyType", dailyType);
                bundle.putInt("achieveId", id);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentManagerLayout, fragment);
                ft.commit();
            }
        });

    }
}
