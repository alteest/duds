package com.prospero.duds.fragment;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prospero.duds.MainActivity;
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
