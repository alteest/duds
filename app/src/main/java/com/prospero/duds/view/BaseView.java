package com.prospero.duds.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.prospero.duds.MainActivity;
import com.prospero.duds.fragment.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public abstract class BaseView extends RelativeLayout {
    protected ImageButton mImageButton;
    protected String mFilepath = null;
    protected Map<String, ShowcaseView> mShowcaseViews = new HashMap<String, ShowcaseView>();
    protected BaseFragment mFragment;

    public BaseView(Context context, BaseFragment fragment) {
        super(context);
        mFragment = fragment;
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(getLayoutResource(), this);
    }

    public void showShowcaseViews() {}

    protected abstract int getLayoutResource();
    public void updateView() {}

    public void setFilepath(String filepath) {
        mFilepath = filepath;
    }

    public String getFilepath() {
        return mFilepath;
    }
    public void doFloatingActionButton() {}
}
