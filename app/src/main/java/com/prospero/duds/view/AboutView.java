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
    protected void onLayout (boolean changed,
                             int left,
                             int top,
                             int right,
                             int bottom) {
        if (changed) {
            mFragment.hideActionButton();
            TextView aboutText = findViewById(R.id.about_text);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) aboutText.getLayoutParams();
            layoutParams.topMargin = getHeight() / 3;
            aboutText.setLayoutParams(layoutParams);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.about_view;
    }
}
