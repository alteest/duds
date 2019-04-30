package com.prospero.duds.view;

import android.annotation.SuppressLint;

import com.prospero.duds.R;
import com.prospero.duds.fragment.BaseFragment;

public class NotFoundView extends BaseView {

    @SuppressLint("RestrictedApi")
    public NotFoundView(BaseFragment fragment) {
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
        return R.layout.not_found_view;
    }
}