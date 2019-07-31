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
    public void setActive() {
        mFragment.hideActionButton();
        super.setActive();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.not_found_view;
    }
}
