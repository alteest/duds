package com.prospero.duds.button;

import android.view.View;

import com.prospero.duds.MainActivity;
import com.prospero.duds.async.SearchSimilaritiesTask;
import com.prospero.duds.fragment.BoxFragment;

public class SendSimilarityButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        BoxFragment fragment = (BoxFragment) MainActivity.activity.getCurrentFragment();
        fragment.showProgressBar();
        new SearchSimilaritiesTask().execute(fragment);
    }
}
