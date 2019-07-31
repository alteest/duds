package com.prospero.duds.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.prospero.duds.MainActivity;
import com.prospero.duds.async.SearchBoxesTask;
import com.prospero.duds.fragment.BaseFragment;
import com.prospero.duds.fragment.UploadFragment;

public abstract class UploadView extends BaseView {

    UploadView(BaseFragment fragment) {
        super(fragment);
    }

    @Override
    public void setActive() {
        if (getFilepath() == null) {
            selectImage();
        }
        mFragment.mFloatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doSearch();
            }
        });
        super.setActive();
    }

    protected abstract void selectImage();

    public abstract void onActivityResult(Intent data);

    @SuppressLint("RestrictedApi")
    void setImage(String filepath) {

        ViewGroup.LayoutParams params = mImageButton.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mImageButton.setLayoutParams(params);

        mImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setFilepath(filepath);
        mImageButton.setImageBitmap((BitmapFactory.decodeFile(filepath)));

        mFragment.showActionButton();
    }

    private void doSearch() {
        UploadFragment fragment = (UploadFragment) MainActivity.activity.getCurrentFragment();
        if (fragment.getFilepath() != null) {
            //mShowcaseViews.get("mImageButtonSelect").hide();
            fragment.showProgressBar();
            new SearchBoxesTask().execute(fragment);
        }

    }
}
