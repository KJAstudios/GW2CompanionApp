package com.example.gw2companionapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datastructures.ParsedDailyAchievements;

import org.jetbrains.annotations.NotNull;



public class LoadingFragment extends Fragment implements DataLoaderFragment.LoaderListener{
    private String dailyType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        dailyType = bundle.getString("dailyType");
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onLoaded() {
        DailyFragment fragment = new DailyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dailyType", dailyType);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentManagerLayout, fragment);
        ft.commit();
            }
}
