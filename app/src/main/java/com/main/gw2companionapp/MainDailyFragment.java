package com.main.gw2companionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.datastructures.ParsedDailyAchievements;
import com.example.gw2companionapp.R;

import org.jetbrains.annotations.NotNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainDailyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainFragmentLayout = inflater.inflate(R.layout.fragment_daily_main, container, false);
        return mainFragmentLayout;
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //click listeners for buttons
        setButtonListener(view, "fractal", R.id.fractal_daily_button);
        setButtonListener(view, "pve", R.id.pve_daily_button);
        setButtonListener(view, "pvp", R.id.pvp_daily_button);
        setButtonListener(view, "wvw", R.id.wvw_daily_button);
    }

    /**
     * sets up a click listener for a button, to call the DailyFragment that displays all the achievments
     * in that category
     * checks if the dailies have been loaded, if not, send to loading screen
     * @param view view to find button
     * @param dailyType sends the type of daily to the DailyFragment
     * @param id id of button to find
     */
    private void setButtonListener(View view, final String dailyType, int id) {
        view.findViewById(id).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ParsedDailyAchievements.checkDailiesLoaded()) {
                            DailyFragment fragment = createDailyFragment(dailyType);
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.fragmentManagerLayout, fragment);
                            ft.commit();
                        }
                        else{
                            LoadingFragment fragment = createLoadingFragment(dailyType);
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.fragmentManagerLayout, fragment);
                            ft.commit();
                        }
                    }
                }
        );

    }

    /**
     * creates the DailyFragment and puts the daily type in bundle to send
     * @param dailyType category of dailies for the DailyFragment to display
     * @return the built DailyFragment
     */
    private DailyFragment createDailyFragment(String dailyType) {
        DailyFragment fragment = new DailyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dailyType", dailyType);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * creates a LoadingFragment that holds the data needed to load the DailyFragment onces
     * the dailies are loaded
     * @param dailyType what dailyType menu to go to after load complete
     * @return the bundled fragment
     */
    private LoadingFragment createLoadingFragment(String dailyType){
        LoadingFragment fragment = new LoadingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dailyType", dailyType);
        fragment.setArguments(bundle);
        return fragment;
    }
}
