package com.prospero.duds.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageButton;

import com.prospero.duds.R;
import com.prospero.duds.ao.Box;
import com.prospero.duds.async.SearchSimilaritiesTask;
import com.prospero.duds.fragment.BaseFragment;

import java.io.ByteArrayOutputStream;

@SuppressLint("ViewConstructor")
public class BoxView extends BaseView {

    @SuppressLint("RestrictedApi")
    public BoxView(BaseFragment fragment) {
        super(fragment);
        mImageButton = findViewById(R.id.box_image_button);
        /*
        mImageButton.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
            }

            @Override
            public void onDoubleClick(View view) {
                BoxFragment fragment = (BoxFragment) MainActivity.activity.getCurrentFragment();
                fragment.showProgressBar();
                new SearchSimilaritiesTask().execute(fragment);
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
                    //BoxFragment fragment = (BoxFragment) MainActivity.activity.getCurrentFragment();
                    mFragment.showProgressBar();
                    new SearchSimilaritiesTask().execute(mFragment);
                }
            });
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.box_view;
    }

    @SuppressLint("RestrictedApi")
    public void setImage(Box box) {
        Bitmap boxBitmap = Bitmap.createBitmap(BitmapFactory.decodeFile(mFilepath),
                box.x1, box.y1, box.x2 - box.x1, box.y2 - box.y1);
        BitmapDrawable bmd = new BitmapDrawable(getResources(), boxBitmap);
        mImageButton.setImageDrawable(bmd);
        //mActionButton.setVisibility(View.VISIBLE);
    }

    @SuppressLint("WrongThread")
    public byte[] getBytes() {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        Bitmap bitmap1 = ((BitmapDrawable) mImageButton.getDrawable()).getBitmap();
        bitmap1.compress(Bitmap.CompressFormat.JPEG,80, bytearrayoutputstream);
        return bytearrayoutputstream.toByteArray();
    }
}
