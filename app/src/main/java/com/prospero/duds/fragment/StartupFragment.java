package com.prospero.duds.fragment;

import android.view.View;

import com.prospero.duds.view.StartupView;

import java.util.ArrayList;

public class StartupFragment extends BaseFragment {

    @Override
    protected ArrayList<View> getViews() {
        ArrayList<View> views = new ArrayList<>();
        views.add(new StartupView(this.getContext()));
        return views;
    }
}
