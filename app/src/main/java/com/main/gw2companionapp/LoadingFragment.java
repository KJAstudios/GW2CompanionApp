package com.main.gw2companionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.gw2companionapp.R;

import org.jetbrains.annotations.NotNull;


public class LoadingFragment extends Fragment implements DataLoaderFragment.LoaderListener {
    private DataLoaderFragment dataLoaderFragment;
    private FragmentActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

    /**
     * listener call if the dailies loaded properly
     */
    @Override
    public void onLoaded() {
        dataLoaderFragment.closeHandler();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentManagerLayout, new MainDailyFragment());
            ft.commit();

    }

    /**
     * listener call if dailies failed to load
     */
    @Override
    public void onFailed() {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentManagerLayout, new ErrorFragment());
        ft.commit();
    }
}
