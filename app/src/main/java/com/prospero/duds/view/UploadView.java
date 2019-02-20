package com.prospero.duds.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.prospero.duds.async.SearchSimilaritiesTask;
import com.prospero.duds.fragment.BoxFragment;
import com.prospero.duds.fragment.UploadFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
