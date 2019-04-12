package com.prospero.duds.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BasePagerAdapter extends PagerAdapter {

    private final ArrayList<View> views;

    public BasePagerAdapter(@NonNull ArrayList<View> views) {
        super();
        this.views = views;
    }

    @Override
    public int getItemPosition (@NonNull Object object)
    {
        int index = views.indexOf (object);
        if (index == -1)
            return POSITION_NONE;
        else
            return index;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem (@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView(views.get(position));
    }

    View getView(int position) {
        if (position >= views.size()) {
            return null;
        }
        return views.get(position);
    }

}
