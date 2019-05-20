package com.prospero.duds.fragment;

import android.view.View;

import com.prospero.duds.view.UploadPhotoView;

import java.util.ArrayList;

public class UploadPhotoFragment extends UploadFragment {

    @Override
    protected ArrayList<View> getViews() {
        ArrayList<View> views = new ArrayList<>();
        views.add(new UploadPhotoView(this));
        return views;
    }
}
