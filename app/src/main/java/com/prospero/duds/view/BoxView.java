package com.prospero.duds.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.ao.Box;
import com.prospero.duds.async.SearchSimilaritiesTask;
import com.prospero.duds.button.DoubleClick;
import com.prospero.duds.button.DoubleClickListener;
import com.prospero.duds.fragment.BoxFragment;

import java.io.ByteArrayOutputStream;

public class BoxView extends BaseView {

    public BoxView(Context context) {
        super(context);
        mImageButton = (ImageButton) findViewById(R.id.box_image_button);
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
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.box_view;
    }

    public void setImage(Box box) {
        Bitmap boxBitmap = Bitmap.createBitmap(BitmapFactory.decodeFile(mFilepath),
                box.x1, box.y1, box.x2 - box.x1, box.y2 - box.y1);
        BitmapDrawable bmd = new BitmapDrawable(boxBitmap);
        mImageButton.setImageDrawable(bmd);
    }

    public byte[] getBytes() {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        Bitmap bitmap1 = ((BitmapDrawable) mImageButton.getDrawable()).getBitmap();
        bitmap1.compress(Bitmap.CompressFormat.JPEG,80, bytearrayoutputstream);
        return bytearrayoutputstream.toByteArray();
    }
}
