package com.prospero.duds.fragment;

import android.view.View;

import com.prospero.duds.view.FeedbackView;

import java.util.ArrayList;

public class FeedbackFragment extends BaseFragment {

    @Override
    protected ArrayList<View> getViews() {
        ArrayList<View> views = new ArrayList<>();
        views.add(new FeedbackView(this));
        return views;
    }
}
