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
import com.prospero.duds.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class SimilarView extends BaseView {

    private ProgressBar mProgressBar;

    private String filename = null;
    private String url = null;
    private String store = null;
    private float distance = -1;

    @SuppressLint("RestrictedApi")
    public SimilarView(BaseFragment fragment) {
        super(fragment);
        mImageButton = findViewById(R.id.similar_image);
        mProgressBar = findViewById(R.id.progress_similar);
        hideProgressBar();

        /*mImageButton.setOnClickListener(new OnClickListener() {
            private static final long DOUBLE_PRESS_INTERVAL = 250;

            @Override
            public void onClick(View view) {
                long pressTime = System.currentTimeMillis();
                if (url != null) {
                    if (pressTime - last <= DOUBLE_PRESS_INTERVAL) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        MainActivity.activity.startActivity(browserIntent);
                    }
                }
                last = pressTime;
            }
        });*/
        /*
        mImageButton.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
            }

            @Override
            public void onDoubleClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                MainActivity.activity.startActivity(browserIntent);
            }
        }));
        */
    }

    @Override
    protected void onLayout (boolean changed,
                             int left,
                             int top,
                             int right,
                             int bottom) {
        if (changed) {
            mFragment.showActionButton();
            mFragment.mFloatingActionButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    MainActivity.activity.startActivity(browserIntent);
                }
            });
        }
        super.onLayout(changed, left, top, right, bottom);
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
    public void setData(JSONObject jsonObj) {
        // {"distance":0.019811499598460822,"name":"MP002XW0F50U_5768008_1_v1.jpeg","url":"https://www.lamoda.ru/p/mp002xw0f50u/clothes-panda-plate/"}
        try {
            url = jsonObj.getString("url");
            filename = jsonObj.getString("name");
            distance = jsonObj.getLong("distance");
            store = jsonObj.getString("store");
            getImage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
