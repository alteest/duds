package com.prospero.duds.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BasePagerAdapter extends PagerAdapter {

    private ArrayList<View> views = null;

    public BasePagerAdapter(@NonNull ArrayList<View> views) {
        super();
        this.views = views;
    }

    @Override
    public int getItemPosition (Object object)
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object)
    {
        container.removeView(views.get(position));
    }

    public View getView(int position) {
        if (position >= views.size()) {
            return null;
        }
        return views.get(position);
    }

}
