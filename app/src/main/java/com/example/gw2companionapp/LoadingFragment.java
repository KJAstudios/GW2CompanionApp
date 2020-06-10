package com.example.gw2companionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;


public class LoadingFragment extends Fragment implements DataLoaderFragment.LoaderListener {
    private DataLoaderFragment dataLoaderFragment;
    private String dailyType;
    private FragmentActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            dailyType = bundle.getString("dailyType");
        }
        activity = getActivity();
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataLoaderFragment = new DataLoaderFragment();
        dataLoaderFragment.addListener(this);
        dataLoaderFragment.checkLoading();

    }

    @Override
    public void onLoaded() {
        dataLoaderFragment.closeHandler();
        DailyFragment fragment = new DailyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dailyType", dailyType);
        fragment.setArguments(bundle);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentManagerLayout, fragment);
        ft.commit();
    }

    public void setDailyType(String dailyType) {
        this.dailyType = dailyType;
    }
}
