package com.prospero.duds.fragment;

import android.view.View;

import com.prospero.duds.view.AboutView;

import java.util.ArrayList;

public class AboutFragment extends BaseFragment {

    @Override
    protected ArrayList<View> getViews() {
        ArrayList<View> views = new ArrayList<>();
        views.add(new AboutView(this));
        return views;
    }
}
