package com.prospero.duds.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.async.GetSimilarImageTask;
import com.prospero.duds.async.PostThumb;
import com.prospero.duds.async.ThumbData;
import com.prospero.duds.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class SimilarView extends BaseView {

    private ProgressBar mProgressBar;

    private String uuid = null;
    private int picture_id;
    private String filename = null;
    private String url = null;
    private String store = null;
    private float distance;

    private ImageButton mImageButtonThumbUp = null;
    private ImageButton mImageButtonThumbDown = null;

    @SuppressLint("RestrictedApi")
    public SimilarView(BaseFragment fragment) {
        super(fragment);
        mImageButton = findViewById(R.id.similar_image);
        mProgressBar = findViewById(R.id.progress_similar);
        mImageButtonThumbUp = findViewById(R.id.similar_button_thumb_up);
        mImageButtonThumbUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                thumb(true);
            }
        });
        mImageButtonThumbDown = findViewById(R.id.similar_button_thumb_down);
        mImageButtonThumbDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                thumb(false);
            }
        });
        hideProgressBar();
    }

    @Override
    public void setActive() {
        mFragment.showActionButton();
        mFragment.mFloatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                MainActivity.activity.startActivity(browserIntent);
            }
        });
        super.setActive();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.similar_view;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    private void getImage() {
        if (mImageButton.getDrawable() == null) {
            showProgressBar();
            new GetSimilarImageTask().execute(this);
        }
    }

    private void thumb(boolean mode) {
        if (mode) {
            mImageButtonThumbUp.setBackgroundResource(R.color.thumb_clicked);
        } else {
            mImageButtonThumbDown.setBackgroundResource(R.color.thumb_clicked);
        }
        new PostThumb().execute(new ThumbData(this, mode));
    }


    public void setData(String uuid, JSONObject jsonObj) {
        // {"distance":0.019811499598460822,"name":"MP002XW0F50U_5768008_1_v1.jpeg","url":"https://www.lamoda.ru/p/mp002xw0f50u/clothes-panda-plate/"}
        this.uuid = uuid;
        try {
            url = jsonObj.getString("url");
            JSONObject picture = jsonObj.getJSONObject("picture");
            picture_id = picture.getInt("id");
            filename = picture.getString("name");
            distance = jsonObj.getLong("distance");
            store = jsonObj.getString("store");
            getImage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getUUID() {
        return uuid;
    }

    public int getPicture() {
        return picture_id;
    }

    public String getFilename() {
        return filename;
    }
    public String getStore() {
        return store;
    }

    public void setImage(Bitmap bitmap) {
        mImageButton.setImageBitmap(bitmap);
    }

    private void showProgressBar() {
        mImageButton.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mImageButton.setVisibility(View.VISIBLE);
    }
}
