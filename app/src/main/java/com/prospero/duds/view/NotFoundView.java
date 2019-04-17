package com.prospero.duds.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageButton;

import com.prospero.duds.R;
import com.prospero.duds.ao.Box;
import com.prospero.duds.async.SearchSimilaritiesTask;
import com.prospero.duds.fragment.BaseFragment;

import java.io.ByteArrayOutputStream;

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
