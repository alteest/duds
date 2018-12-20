package com.prospero.duds.button;

import android.view.View;

import com.prospero.duds.MainActivity;
import com.prospero.duds.async.SearchBoxesRequest;
import com.prospero.duds.async.SearchBoxesTask;
import com.prospero.duds.fragment.UploadFragment;

public class SendSearchButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {

        UploadFragment fragment = (UploadFragment) MainActivity.activity.getCurrentFragment();
        //String filepath = fragment.getFilepath();
        if (fragment.getFilepath() != null) {
            //new SearchBoxesTask().execute(new SearchBoxesRequest(url, filepath));
            fragment.showProgressBar();
            new SearchBoxesTask().execute(fragment);
        }
    }
}
