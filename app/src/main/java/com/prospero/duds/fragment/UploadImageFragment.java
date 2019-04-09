package com.prospero.duds.fragment;

import android.view.View;

import com.prospero.duds.view.UploadImageView;

import java.util.ArrayList;

public class UploadImageFragment extends UploadFragment {

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((data == null) && (views.size() < 1)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            UploadImageView view = (UploadImageView) views.get(0);
            view.onActivityResult(data);
        }
    }
*/

    @Override
    protected ArrayList<View> getViews() {
        ArrayList<View> views = new ArrayList<>();
        views.add(new UploadImageView(this));
        return views;
    }
}
