package com.example.gw2companionapp;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
        TextView reqText = view.findViewById(R.id.achieve_reqs);
        nameText.setText(achievement.getName());
        //if there isn't an achievement description, don't show it

        if (!(achievement.getDescription().equals(""))) {
            addDescText(view, achievement.getDescription());
        }
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
    private void addDescText(View view, String description) {
        //get the layout for the fragment
        ConstraintLayout layout = view.findViewById(R.id.achievement_layout);

        //make the TextView from the template given
        TextView text = (TextView) getLayoutInflater().inflate(
                R.layout.achievement_body_text_template, null);

        //set the text wrapping via ViewGroup.LayoutParams
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //now set everything that needs to be set, and add to the layout
        text.setLayoutParams(layoutParams);
        layout.addView(text);
        text.setText(description);
        text.setId(R.id.acheive_desc);


        //before leaving, change the constraints for the textViews
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);
        //set constraints of added description box
        set.connect(view.findViewById(R.id.acheive_desc).getId(), ConstraintSet.TOP,
                view.findViewById(R.id.achieve_name).getId(), ConstraintSet.BOTTOM, getDpFromPx(16));
        set.connect(view.findViewById(R.id.acheive_desc).getId(), ConstraintSet.START,
                view.findViewById(R.id.achieve_name).getId(), ConstraintSet.START, getDpFromPx(24));
        set.connect(view.findViewById(R.id.acheive_desc).getId(), ConstraintSet.END,
                view.findViewById(R.id.achieve_name).getId(), ConstraintSet.END, getDpFromPx(24));

        //set the constrains of the requirements box
        set.connect(view.findViewById(R.id.achieve_reqs).getId(), ConstraintSet.TOP,
                view.findViewById(R.id.acheive_desc).getId(), ConstraintSet.BOTTOM, getDpFromPx(16));
        set.applyTo(layout);

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
