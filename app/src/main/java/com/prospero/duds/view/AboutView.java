package com.prospero.duds.view;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prospero.duds.R;
import com.prospero.duds.fragment.BaseFragment;

public class AboutView extends BaseView {

    public AboutView(BaseFragment fragment) {
        super(fragment);
    }

    @Override
    public void setActive() {
        mFragment.hideActionButton();
        TextView aboutText = findViewById(R.id.about_text);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) aboutText.getLayoutParams();
        layoutParams.topMargin = getHeight() / 3;
        aboutText.setLayoutParams(layoutParams);
        super.setActive();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.about_view;
    }
}
