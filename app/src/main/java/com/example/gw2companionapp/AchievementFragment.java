package com.example.gw2companionapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datastructures.FullAchievement;
import com.datastructures.ParsedDailyAchievements;

import org.jetbrains.annotations.NotNull;

public class AchievementFragment extends Fragment {
    private String dailyType;
    private int id;
    //TODO build this fragment to display an achievement (maybe just for dailies? idk)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //unpackage the data from the bundle
        Bundle bundle = this.getArguments();
        dailyType = bundle.getString("dailyType");
        id = bundle.getInt("achieveId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.achievement_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        //get the achievement data
        ParsedDailyAchievements dailyAchievements = ParsedDailyAchievements.getInstance(MainActivity.context);
        FullAchievement achievement = dailyAchievements.getAchievement(dailyType, id);

        //set text fields
        TextView nameText = view.findViewById(R.id.achieve_name);
        TextView descText = view.findViewById(R.id.achieve_description);
        TextView reqText = view.findViewById(R.id.achieve_reqs);
        nameText.setText(achievement.getName());
        descText.setText(achievement.getDescription());
        reqText.setText(achievement.getRequirement());

        //click listener for back button
        view.findViewById(R.id.back_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        DailyFragment dailyFragment = makeReturnFragment();
                        ft.replace(R.id.fragmentManagerLayout, dailyFragment);
                        ft.commit();
                    }
                }
        );
    }

    /**
     * bundles the return type for the back button
     * @return DailyFragment bundled with info needed to load
     */
    public DailyFragment makeReturnFragment(){
        DailyFragment fragment = new DailyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dailyType", dailyType);
        fragment.setArguments(bundle);
        return fragment;
    }
}
