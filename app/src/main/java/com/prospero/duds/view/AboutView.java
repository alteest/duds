package com.prospero.duds.view;

import android.annotation.SuppressLint;

import com.prospero.duds.R;
import com.prospero.duds.fragment.BaseFragment;

public class AboutView extends BaseView {

    //@SuppressLint("RestrictedApi")
    public AboutView(BaseFragment fragment) {
        super(fragment);
    }

    @Override
    protected void onLayout (boolean changed,
                             int left,
                             int top,
                             int right,
                             int bottom) {
        if (changed) {
            mFragment.hideActionButton();
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.about_view;
    }
}
