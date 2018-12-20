package com.prospero.duds.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.async.SearchBoxesTask;
import com.prospero.duds.fragment.UploadFragment;

public abstract class UploadView extends BaseView {

    public UploadView(Context context) {
        super(context);
    }

    @Override
    public void showShowcaseViews() {

        Button button = new Button(getContext());
        button.setText("");
        button.setEnabled(false);
        button.setVisibility(View.GONE);

        ShowcaseView.Builder builder = new ShowcaseView.Builder(MainActivity.activity)
                .setTarget(new ViewTarget(mImageButton))
                .replaceEndButton(button)
                .setStyle(R.style.MyCustomShowcaseTheme)
                .hideOnTouchOutside();
        if (mFilepath == null) {
            builder.setContentText(R.string.helper_upload_image_button_text);
        } else {
            builder.setContentText(R.string.helper_upload_image_button_send_text);
        }
        builder.build();
    }

    public abstract void onActivityResult(Intent data);

    public void setImage(String filepath) {

        ViewGroup.LayoutParams params = mImageButton.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mImageButton.setLayoutParams(params);

        mImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setFilepath(filepath);
        mImageButton.setImageBitmap((BitmapFactory.decodeFile(filepath)));

        showShowcaseViews();

        //mShowcaseViews.get("mImageButton").hide();
        //mShowcaseViews.get("mImageButtonSelect").show();
    }

    protected void doSearch() {
        UploadFragment fragment = (UploadFragment) MainActivity.activity.getCurrentFragment();
        if (fragment.getFilepath() != null) {
            //mShowcaseViews.get("mImageButtonSelect").hide();
            fragment.showProgressBar();
            new SearchBoxesTask().execute(fragment);
        }
    }
}
