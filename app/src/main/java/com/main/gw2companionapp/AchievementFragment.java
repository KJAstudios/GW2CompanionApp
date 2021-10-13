package com.main.gw2companionapp;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.datastructures.FullAchievement;
import com.datastructures.ParsedDailyAchievements;
import com.example.gw2companionapp.R;
import com.jsonclasses.ItemClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
        FullAchievement achievement = dailyAchievements.getAchieveList(dailyType).get(id);

        //set text fields
        TextView nameText = view.findViewById(R.id.achieve_name);
        TextView reqText = view.findViewById(R.id.achieve_reqs);
        TextView rewardText = view.findViewById(R.id.achieve_reward);

        nameText.setText(achievement.getName());
        //if there isn't an achievement description, don't show it
        if (doesDescriptionExist(achievement)) {
            setDescText(view, achievement.getDescription());
        } else{
            view.findViewById(R.id.acheive_desc).setVisibility(View.GONE);
            view.findViewById(R.id.DescriptionTitle).setVisibility(View.GONE);
        }
        reqText.setText(achievement.getRequirement());

        //set and display rewards
        ArrayList<ItemClass> rewardList = achievement.getRewardList();
        String rewards = "";
        for (int i = 0; i < rewardList.size(); i++) {
            if (rewardList.size() == 1) {
                rewards = rewardList.get(i).getName();
            } else {
                rewards = rewards + rewardList.get(i).getName() + "\n";
            }
        }
        rewardText.setText(rewards);
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
     *
     * @return DailyFragment bundled with info needed to load
     */
    public DailyFragment makeReturnFragment() {
        DailyFragment fragment = new DailyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dailyType", dailyType);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * adds the description text box to the fragment if it's there
     * not sure if this needs abstraction, as it's only used here right now
     *
     * @param view        view to create text in
     * @param description what to set the text to
     */
    private void setDescText(View view, String description) {
        TextView DescriptionText = view.findViewById(R.id.acheive_desc);
        DescriptionText.setText(description);
    }

    private boolean doesDescriptionExist(FullAchievement achievement){
        if(achievement.getDescription().equals("")){
            return false;
        }
        return true;
    }

    /**
     * converter from px to dp
     *
     * @param px value in px
     * @return value in dp
     */
    private int getDpFromPx(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
                MainActivity.context.getResources().getDisplayMetrics());
    }
}
