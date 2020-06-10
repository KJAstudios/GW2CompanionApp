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
        //creates the list of acheivements
        populateAchievementList(view);

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
        for (FullAchievement achievement : parsedAchievements.getAchieveList(dailyType)) {
            Button button = createButton(achievement);
            achievementList.addView(button);
            setButtonListener(achievement);
        }
    }

    /**
     * creates the button with achievement info
     * @param achievement what achievement to display the name of
     * @return
     */
    private Button createButton(FullAchievement achievement) {
        Button button = new Button(MainActivity.context);
        button.setId(achievement.getId());
        button.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        button.setText(achievement.getName());
        return button;
    }

    /**
     * sets the click listeners for the achievement list by bundling required info for fragment
     * @param achievement the achievement to set
     */
    private void setButtonListener(final FullAchievement achievement) {
        getView().findViewById(achievement.getId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AchievementFragment fragment = new AchievementFragment();
                Bundle bundle = new Bundle();
                bundle.putString("dailyType", dailyType);
                bundle.putInt("achieveId", achievement.getId());
                fragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentManagerLayout, fragment);
                ft.commit();
            }
        });

    }
}
