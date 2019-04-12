package com.prospero.duds.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.async.SearchBoxesTask;
import com.prospero.duds.fragment.BaseFragment;
import com.prospero.duds.fragment.UploadFragment;

public abstract class UploadView extends BaseView {

    public UploadView(BaseFragment fragment) {
        super(fragment);
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

    @Override
    protected void onLayout (boolean changed,
                             int left,
                             int top,
                             int right,
                             int bottom) {
        if (getFilepath() == null) {
            selectImage();
        }
        super.onLayout(changed, left, top, right, bottom);
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
        mFragment.mFloatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doSearch();
            }
        });
        //showShowcaseViews();

        //mShowcaseViews.get("mImageButton").hide();
        //mShowcaseViews.get("mImageButtonSelect").show();
    }

    private void doSearch() {

        /*final Dialog dialog = new Dialog(MainActivity.activity);
        dialog.setContentView(R.layout.dialog_upload);
        //dialog.setTitle("Title...");

        Button buttonBox = (Button) dialog.findViewById(R.id.dialog_upload_search_box);
        buttonBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                UploadFragment fragment = (UploadFragment) MainActivity.activity.getCurrentFragment();
                if (fragment.getFilepath() != null) {
                    //mShowcaseViews.get("mImageButtonSelect").hide();
                    fragment.showProgressBar();
                    new SearchBoxesTask().execute(fragment);
                }
            }
        });

        Button buttonSimilar = (Button) dialog.findViewById(R.id.dialog_upload_search_similar);
        buttonSimilar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                UploadFragment uploadFragment = (UploadFragment) MainActivity.activity.getCurrentFragment();
                BoxFragment fragment = new BoxFragment();
                Bundle bundle = new Bundle();
                String filepath = uploadFragment.getFilepath();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filepath, options);
                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
                //[{"box":[192,173,561,657],"label":0,"score":1}]
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("label", 0);
                    jsonObject.put("score", 1);
                    JSONArray box = new JSONArray();
                    box.put(0);
                    box.put(0);
                    box.put(imageHeight);
                    box.put(imageWidth);
                    jsonObject.put("box", box);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
                //[{"box":[192,173,561,657],"label":0,"score":1}]
                bundle.putSerializable("array", jsonArray.toString());
                bundle.putString("filepath", uploadFragment.getFilepath());
                fragment.setArguments(bundle);
                new SearchSimilaritiesTask().execute(fragment);

            }
        });

        dialog.show(); */
        // THIS IS REAL SEARCH

        UploadFragment fragment = (UploadFragment) MainActivity.activity.getCurrentFragment();
        if (fragment.getFilepath() != null) {
            //mShowcaseViews.get("mImageButtonSelect").hide();
            fragment.showProgressBar();
            new SearchBoxesTask().execute(fragment);
        }

    }
}
